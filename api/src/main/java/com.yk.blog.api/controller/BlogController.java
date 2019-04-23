package com.yk.blog.api.controller;

import com.yk.blog.core.dto.BlogReqDTO;
import com.yk.blog.core.dto.BlogRespDTO;
import com.yk.blog.core.dto.GenericResult;
import com.yk.blog.core.dto.Result;
import com.yk.blog.core.service.BlogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("获取浏览量前十的博客标题列表，降序排序")
    @GetMapping("mostInterviewed")
    public GenericResult<List<BlogRespDTO>> getMostInterviewedBlogList(){
        return blogService.getMostInterviewedBlogList();
    }

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
    @DeleteMapping("{blogId}")
    public Result deleteBlog(@ApiParam("博客id") @PathVariable("blogId") int blogId,
                             @ApiParam("用户的token") @RequestParam("token") String token) {
        return blogService.deleteBlog(blogId,token);
    }

    @ApiOperation("更新博客内容")
    @PutMapping("")
    public Result updateBlog(@ApiParam("博客相关信息") @Valid @RequestBody BlogReqDTO blog,
                             @ApiParam("用户的token") @RequestParam("token") String token) {
        return blogService.updateBlog(blog,token);
    }

    @ApiOperation("新建博客")
    @PostMapping("")
    public GenericResult<Integer> createBlog(@ApiParam("用户的token") @RequestParam("token") String token,
                                             @ApiParam("博客相关信息") @Valid @RequestBody BlogReqDTO blog) {
        return blogService.createBlog(blog,token);
    }


}
