package com.yk.blog.core.service;


import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.BlogReqDTO;
import com.yk.blog.core.dto.BlogRespDTO;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */

public interface BlogService {

    /**
     *  获取推荐文章
     *  @author  yikang
     *  @date  2019/4/24
     *  @param
     *  @return
     */
    GenericResult<List<BlogRespDTO>> getRecommendBlog(String token);

    /**
     *  获取博客阅读量最多的十个博客
     *  @author  yikang
     *  @date  2019/2/25
     *  @param
     *  @return
     */
    GenericResult<List<BlogRespDTO>> getMostInterviewedBlogList();

    /**
     * 更新博客的评论数目
     *
     * @param id    博客id
     * @param count 评论数目
     * @Author yikang
     * @Date 2018/9/10
     */
    void updateBlogCommentCount(int id, int count);

    /**
     * 通过用户id获取博客列表
     *
     * @param userId 被查询博客用户id
     * @return 返回博客列表
     * @Author yikang
     * @Date 2018/9/3
     */
    GenericResult<List<BlogRespDTO>> getBlogsByUserId(String userId);

    /**
     * 获得用户的特定博客
     *
     * @param blogId 博客id
     * @return 获得博客详情
     * @Author yikang
     * @Date 2018/9/3
     */
    GenericResult<BlogRespDTO> getBlogById(int blogId);


    /**
     * 删除博客
     *
     * @param blogId 被删除博客id
     * @param token  登录用户的token
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    Result deleteBlog(int blogId, String token);

    /**
     * 更新博客内容
     *
     * @param blog  博客更新后的内容
     * @param token
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    Result updateBlog(BlogReqDTO blog, String token);

    /**
     * 新建博客
     *
     * @param blog  博客内容
     * @param token
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    GenericResult<Integer> createBlog(BlogReqDTO blog, String token);

}
