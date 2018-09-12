package com.yk.blog.api.controller;

import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.dto.BlogReqDTO;
import com.yk.blog.core.dto.BlogRespDTO;
import com.yk.blog.core.service.BlogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yikang
 * @date 2018/8/29
 */
@RestController
@RequestMapping("blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @ApiOperation("通过用户id获取博客列表")
    @GetMapping("{userId}/blogs")
    public GenericResult<List<BlogRespDTO>> getBlogsByUserId(@ApiParam("用户id") @PathVariable("userId") String userId) {
        return blogService.getBlogsByUserId(userId);
    }

    @ApiOperation("获得用户的特定博客")
    @GetMapping("{blogId}")
    public GenericResult<BlogRespDTO> getBlogById(@ApiParam("博客id") @PathVariable("blogId") int blogId) {
        return blogService.getBlogById(blogId);
    }

    @ApiOperation("删除博客")
    @DeleteMapping("{userId}/{blogId}")
    public Result deleteBlog(@ApiParam("登录用户id") @PathVariable("userId") String userId,
                             @ApiParam("博客id") @PathVariable("blogId") int blogId) {
        return blogService.deleteBlog(userId, blogId);
    }

    @ApiOperation("更新博客内容")
    @PutMapping("")
    public Result updateBlog(@ApiParam("博客相关信息") @Valid @RequestBody BlogReqDTO blog) {
        return blogService.updateBlog(blog);
    }

    @ApiOperation("新建博客")
    @PostMapping("")
    public Result createBlog(@ApiParam("博客相关信息") @Valid @RequestBody BlogReqDTO blog) {
        return blogService.createBlog(blog);
    }


}
