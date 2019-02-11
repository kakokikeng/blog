package com.yk.blog.api.jspClass;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yikang
 * @date 2019/1/16
 */
@Controller
public class Index {

    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    @GetMapping("404")
    public String notFound(){
        return "404";
    }

}
