package com.yk.blog.core.service;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.BlogReqDTO;
import com.yk.blog.core.dto.BlogRespDTO;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */

public interface BlogService {

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
     * @param userId 登录用户id
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    Result deleteBlog(String userId, int blogId);

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
    Result updateBlog(String userId, int blogId, BlogReqDTO blog);

    /**
     * 新建博客
     *
     * @param blog   博客内容
     * @return
     * @Author yikang
     * @Date 2018/9/3
     */
    Result createBlog(BlogReqDTO blog);

}
