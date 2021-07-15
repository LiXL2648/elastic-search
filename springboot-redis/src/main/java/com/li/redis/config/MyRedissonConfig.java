package com.li.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {

        // 创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);

        // 根据Config 创建出 RedissonClient对象
        return Redisson.create(config);
    }
}
