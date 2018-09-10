package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Mapper
public interface BlogMapper {

    /**
     * 更新博客的评论数量
     *
     * @param id    博客id
     * @param count 评论总数
     * @Author yikang
     * @Date 2018/9/10
     */
    void updateBlogCommentCount(int id, int count);

    /**
     * 通过 博客id -> 阅读量总量 的键值对进行阅读量的批量更新
     *
     * @param map 博客id -> 阅读量总量 键值对
     * @Author yikang
     * @Date 2018/9/10
     */
    void updateReadCountByMap(Map<String, String> map);

    /**
     * 更新点赞数
     *
     * @param count 点赞数量
     * @return
     * @Author yikang
     * @Date 2018/9/10
     */
    int updateLikeCount(int count);

    /**
     * 通过用户id获取博客列表,根据创建时间降序
     *
     * @param userId 被查询博客用户id
     * @return 返回博客列表
     * @Author yikang
     * @Date 2018/9/3
     */
    List<Blog> getBlogsByUserId(String userId);

    /**
     * 通过博客id获得博客
     *
     * @param id 博客id
     * @return 获得博客详情
     * @Author yikang
     * @Date 2018/9/3
     */
    Blog getBlogById(int id);

    /**
     * 通过博客id对博客进行点赞
     *
     * @param id 当前博客id
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    int increaseLikeCount(int id);

    /**
     * 删除博客
     *
     * @param id     被删除博客id
     * @param userId 登录用户id
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    int deleteBlog(@Param("userId") String userId, @Param("id") int id);

    /**
     * 更新博客内容
     *
     * @param userId 登录用户id
     * @param id     博客id
     * @param blog   博客更新后的内容
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    int updateBlog(@Param("userId") String userId, @Param("id") int id, @Param("blog") Blog blog);

    /**
     * 新建博客
     *
     * @param blog 博客相关内容
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    int createBlog(Blog blog);


}
