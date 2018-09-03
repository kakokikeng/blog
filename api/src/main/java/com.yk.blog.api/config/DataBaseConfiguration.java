package com.yk.blog.api.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author yikang
 * @date 2018/8/31
 */
@Configuration
public class DataBaseConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource generateDruidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public JdbcTemplate generateJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(generateDruidDataSource());
        return jdbcTemplate;
    }
}
