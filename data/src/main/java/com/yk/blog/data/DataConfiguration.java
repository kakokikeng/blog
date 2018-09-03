package com.yk.blog.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yikang
 * @date 2018/8/28
 */
@Configuration
@ComponentScan
@MapperScan("com.yk.blog.data.dao")
public class DataConfiguration {
}
