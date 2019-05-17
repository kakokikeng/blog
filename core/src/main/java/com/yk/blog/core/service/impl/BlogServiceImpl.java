package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.constant.ErrorMessages;
import com.yk.blog.core.dto.*;
import com.yk.blog.core.factories.BlogReqFactory;
import com.yk.blog.core.factories.BlogRespFactory;
import com.yk.blog.core.service.*;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.MatrixConstructionUtils;
import com.yk.blog.core.utils.Utils;
import com.yk.blog.data.dao.BlogMapper;
import com.yk.blog.domain.dto.Blog;
import com.yk.blog.domain.dto.Collection;
import com.yk.blog.domain.dto.Record;
import com.yk.blog.domain.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.yk.blog.core.utils.GenericResultUtils.generateResultWithCount;
import static com.yk.blog.core.utils.UserUtils.wrongUserIdGenericResult;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    CountService countService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    JedisPool jedisPool;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    BlogRespFactory blogRespFactory;

    @Autowired
    BlogReqFactory blogReqFactory;

    @Autowired
    CollectionService collectionService;

    @Autowired
    RecordService recordService;


    @Override
    public GenericResult<Boolean> ifCollected(int blogId, String token) {

        try (Jedis jedis = jedisPool.getResource()) {
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
            if (collectionService.getCollection(userId, blogId) == null) {
                return GenericResultUtils.genericResult(true,false);
            } else {
                return GenericResultUtils.genericResult(true,true);
            }
        }

    }

    @Override
    public GenericResult<Boolean> cancelCollect(int blogId, String token) {
         try(Jedis jedis = jedisPool.getResource()){
             String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
             if(collectionService.cancelCollect(userId,blogId) > 0){
                 return GenericResultUtils.genericResult(true,true);
             }else{
                 return GenericResultUtils.genericResult(true,false);
             }
         }

    }

    @Override
    public GenericResult<List<BlogRespDTO>> getCollectionByToken(String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
            List<Collection> collections = collectionService.getCollection(userId);
            List<Integer> blogIds = new ArrayList<>();
            for (int i = 0; i < collections.size(); i++) {
                blogIds.add(collections.get(i).getBlogId());
            }
            List<Blog> blogs = blogMapper.getBlogsByIds(blogIds);
            List<BlogRespDTO> data = new ArrayList<>(blogs.size());
            for (int i = 0; i < blogs.size(); i++) {
                data.add(new BlogRespDTO(blogs.get(i)));
            }
            return GenericResultUtils.genericResult(true,data);
        }
    }

    @Override
    public Result collectBlog(int blogId, String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
            return GenericResultUtils.generateResultWithCount(collectionService.createCollection(userId, blogId));
        }
    }

    @Override
    public GenericResult<List<BlogRespDTO>> searchBlogs(String searchContent) {
        String newString = "%";
        char [] chars = searchContent.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            newString += chars[i] + "%";
        }
        List<Blog> blogs = blogMapper.searchBlogs(newString);
        List<BlogRespDTO> data = new ArrayList<>();
        for (int i = 0; i < blogs.size(); i++) {
           data.add(new BlogRespDTO(blogs.get(i)));
        }
        return GenericResultUtils.genericResult(true,data);
    }

    @Override
    public GenericResult<List<BlogRespDTO>> getRecommendBlog(String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
            List<Blog> blogs = blogMapper.getBlogsByRandom(Constant.RECOMMEND_SRC_NUM);
            List<User> users = userService.getUsersByRandom(Constant.RECOMMEND_SRC_NUM);
            boolean in = false;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(userId)) {
                    in = true;
                    break;
                }
            }
            //将获取推荐文章的用户加入用户队列最后一个
            if (!in) {
                users.remove(0);
                User u = new User();
                UserRespDTO user = userService.getLoginUserInfo(token);
                u.setUserName(user.getUserName());
                u.setEmail(user.getEmail());
                u.setCreateTime(new Timestamp(user.getCreateTime()));
                u.setId(user.getId());
                u.setFans(user.getFans());
                u.setBlogs(user.getBlogs());
                u.setFollows(user.getFollows());
                users.add(u);
            }
            //userList转为userIds
            List<String> userIds = new ArrayList<>(users.size());
            for (int i = 0; i < users.size(); i++) {
                userIds.add(users.get(i).getId());
            }

            //blogList转为blogIds
            List<Integer> blogIds = new ArrayList<>(blogs.size());
            for (int i = 0; i < blogs.size(); i++) {
                blogIds.add(blogs.get(i).getId());
            }

            List<Record> records = recordService.getRecords(userIds, blogIds);
            String[][] input = new String[3][blogs.size()];
            for (int i = 0; i < records.size(); i++) {
                input[0][i] = records.get(i).getUserId();
                input[1][i] = String.valueOf(records.get(i).getBlogId());
                input[2][i] = String.valueOf(records.get(i).getScore());
            }
            int[][] scoreMatrix = MatrixConstructionUtils.scoreMatrix(userIds, blogIds, input);
            int[][] similarMatrix = MatrixConstructionUtils.similarMatrix(scoreMatrix, blogIds);
            int[][] resultMatrix = MatrixConstructionUtils.multiplyMatrix(scoreMatrix, similarMatrix);
            //recommendScore数组存储对该用户推荐文章的预估评分，第一行表示文章id，第二行表示文章推荐预估评分
            int[][] recommendScore = new int[2][scoreMatrix.length];
            for (int i = 0; i < resultMatrix[0].length; i++) {
                recommendScore[0][i] = blogIds.get(i);
                recommendScore[1][i] = resultMatrix[resultMatrix.length][i];
            }
            bubble(recommendScore);

            List<Integer> respBlogIds = new ArrayList<>();
            int index = recommendScore[0].length - 1;
            for (int i = 0; i < Constant.RECOMMEND_RETURN_NUM; i++) {
                respBlogIds.add(recommendScore[1][index --]);
            }

            List<Blog> respBlogs = blogMapper.getBlogsByIds(respBlogIds);
            List<BlogRespDTO> data = new ArrayList<>(respBlogs.size());
            for (int i = 0; i < respBlogs.size(); i++) {
                data.add(new BlogRespDTO(respBlogs.get(i)));
            }
            return GenericResultUtils.genericResult(true,data);
        }

    }

    private void bubble(int[][] arr) {
        for (int i = 0; i < arr[0].length - 1; i++) {
            for (int j = 0; j < arr[0].length - i - 1; j++) {
                if (arr[1][j] > arr[1][j + 1]) {
                    int num = arr[1][j];
                    int id = arr[0][j];
                    arr[1][j] = arr[1][j + 1];
                    arr[0][j] = arr[0][j + 1];
                    arr[0][j + 1] = id;
                    arr[1][j + 1] = num;
                }
            }
        }
    }

    @Override
    public GenericResult<UserRespDTO> getOwnerByBlogId(int blogId) {

        String ownerId = blogMapper.getOwnerIdByBlogId(blogId);
        List<String> userIds = new ArrayList<>();
        userIds.add(ownerId);
        List<UserRespDTO> user = userService.getUserListByIdList(userIds);
        return GenericResultUtils.genericResult(true,user.get(0));
    }

    @Override
    public GenericResult<List<BlogRespDTO>> getMostInterviewedBlogList() {
        List<Blog> blogs = blogMapper.getMostInterviewedBlogList();
        List<BlogRespDTO> data = new ArrayList<>(blogs.size());
        for (Blog blog : blogs
        ) {
            data.add(new BlogRespDTO(blog));
        }
        return GenericResultUtils.genericResult(true, data);
    }

    @Override
    public void updateBlogCommentCount(int id, int count) {
        blogMapper.updateBlogCommentCount(id, count);
    }

    @Override
    public GenericResult<List<BlogRespDTO>> getBlogsByToken(String token) {
        try(Jedis jedis = jedisPool.getResource()){
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
            return getBlogsByUserId(userId);
        }
    }

    @Override
    public GenericResult<List<BlogRespDTO>> getBlogsByUserId(String userId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdGenericResult();
        }
        List<Blog> blogList = blogMapper.getBlogsByUserId(userId);
        List<BlogRespDTO> data = new ArrayList<>();
        if (blogList != null) {
            for (Blog blog : blogList) {
                BlogRespDTO tmp = blogRespFactory.createBlogDtoByBlog(blog);
                tmp.setReadCount(countService.getReadCount(tmp.getId()));
                data.add(tmp);
            }
        }
        return GenericResultUtils.genericResult(Boolean.TRUE, data);
    }

    @Override
    public GenericResult<BlogRespDTO> getBlogById(int blogId) {

        Blog blog = blogMapper.getBlogById(blogId);
        if (blog != null) {
            BlogRespDTO tmp = blogRespFactory.createBlogDtoByBlog(blog);
            tmp.setReadCount(countService.getReadCount(tmp.getId()));
            return GenericResultUtils.genericResult(Boolean.TRUE, tmp);
        } else {
            return GenericResultUtils.genericFailureResult(ErrorMessages.BLOG_NOT_EXIST.message, ErrorMessages.BLOG_NOT_EXIST.code);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteBlog(int blogId, String token) {
        if (!authorityService.verifyToken(token)) {
            return GenericResultUtils.genericNormalResult(false, ErrorMessages.TOKEN_NOT_AVAILABLE.message);
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID), token);
            //外键依赖，先删除评论
            commentService.deleteCommentByBlogId(blogId);
            int count = blogMapper.deleteBlog(userId, blogId);
            if (count > 0) {
                jedis.srem(Utils.generatePrefix(Constant.EXIST_BLOG), String.valueOf(blogId));
                jedis.hdel(Utils.generatePrefix(Constant.BLOG_READ_COUNT), String.valueOf(blogId));
                jedis.hdel(Utils.generatePrefix(Constant.BLOG_COMMENT_COUNT), String.valueOf(blogId));
                jedis.hdel(Utils.generatePrefix(Constant.BLOG_LIKED_COUNT), String.valueOf(blogId));
                jedis.srem(Utils.generatePrefix(Constant.BLOG_LIKED_RECORD + userId), String.valueOf(blogId));
            }
            countService.updateBlogCount(userId, -1);
            return generateResultWithCount(count);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateBlog(BlogReqDTO blogReqDTO, String token) {
        if (!authorityService.verifyToken(token)) {
            return GenericResultUtils.genericNormalResult(false, ErrorMessages.TOKEN_NOT_AVAILABLE.message);
        }
        Blog updateBlog = blogReqFactory.createBlogByDto(blogReqDTO);
        int count = blogMapper.updateBlog(updateBlog);
        //TODO 若要推送博客，更新也需要推送
        return generateResultWithCount(count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GenericResult<Integer> createBlog(BlogReqDTO blogReqDTO, String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (!authorityService.verifyToken(token)) {
                return GenericResultUtils.genericFailureResult(ErrorMessages.TOKEN_NOT_AVAILABLE.message);
            }
            String userId = jedis.hget(Utils.generatePrefix(Constant.TOKEN_WITH_USER_ID),token);
            blogReqDTO.setUserId(userId);
            Blog blog = blogReqFactory.createBlogByDto(blogReqDTO);
            int count = blogMapper.createBlog(blog);
            if (count > 0) {
                jedis.sadd(Utils.generatePrefix(Constant.EXIST_BLOG), String.valueOf(blog.getId()));
                countService.updateBlogCount(userId, 1);
            }
            //todo 对所有关注该用户的用户生成一条消息
            return GenericResultUtils.genericResult(true,blog.getId());
        }
    }
}
