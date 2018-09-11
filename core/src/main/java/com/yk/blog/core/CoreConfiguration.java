package com.yk.blog.core;

import com.yk.blog.data.DataConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yikang
 * @date 2018/8/28
 */
@Configuration
@EnableTransactionManagement
@ComponentScan
@Import(DataConfiguration.class)
public class CoreConfiguration {
}
