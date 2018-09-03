package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Mapper
public interface BlogMapper {

    /**
     * 通过用户id获取博客列表
     *
     * @param userId 被查询博客用户id
     * @return 返回博客列表
     * @Author yikang
     * @Date 2018/9/3
     */
    List<Blog> getBlogsByUserId(String userId);

    /**
     * 获得用户的特定博客
     *
     * @param blogId 博客id
     * @param userId 用户ID
     * @return 获得博客详情
     * @Author yikang
     * @Date 2018/9/3
     */
    Blog getBlogByUserIdAndBlogId(String userId, String blogId);

    /**
     * 通过登录用户和博客id对博客进行点赞
     *
     * @param userId 登录用户id
     * @param blogId 当前博客id
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    int increaseLikeCount(String userId, int blogId);

    /**
     * 删除博客
     *
     * @param blogId 被删除博客id
     * @param userId 登录用户id
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    int deleteBlog(String userId, int blogId);

    /**
     * 更新博客内容
     *
     * @param userId 登录用户id
     * @param blogId 博客id
     * @param blog   博客更新后的内容
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    int updateBlog(String userId, int blogId, Blog blog);

    /**
     *  新建博客
     * @param map 博客相关内容
     * @return
     *  @Author yikang
     *  @Date 2018/9/3
     */
    int createBlog(Map map);


}
