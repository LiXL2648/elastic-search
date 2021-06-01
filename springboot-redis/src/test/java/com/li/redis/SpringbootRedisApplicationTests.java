package com.li.redis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringbootRedisApplicationTests {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void testJedisCluster() {

        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.230.24", 6380));
        nodes.add(new HostAndPort("192.168.230.24", 6379));
        nodes.add(new HostAndPort("192.168.230.24", 6381));
        nodes.add(new HostAndPort("192.168.230.24", 6389));
        nodes.add(new HostAndPort("192.168.230.24", 6390));
        nodes.add(new HostAndPort("192.168.230.24", 6391));
        JedisCluster jedisCluster = new JedisCluster(nodes);

        String name1 = jedisCluster.get("name1");
        System.out.println(name1);
    }

    @Test
    public void testRedisTemplate() {
        redisTemplate.opsForValue().set("name5", "WangHP");

        System.out.println(redisTemplate.opsForValue().get("name5"));
    }

    public void test() {

    }
}
