package com.yk.blog.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author yikang
 * @date 2018/9/4
 */
@Configuration
@ConfigurationProperties(prefix = "redis")
public class JedisConfig {

    private String url;
    private int port;

    @Bean
    public JedisPool getJedisPool() {
        JedisPool pool = new JedisPool(url,port);
        return pool;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
