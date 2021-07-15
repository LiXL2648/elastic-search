package com.li.redis;

import com.li.redis.util.RsaEncryptDecryptUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RedissonClient;
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

    @Resource
    private RedissonClient redisson;

    @Test
    public void testRedissonClient() {
        System.out.println(redisson);
    }

    @Test
    public void testRsaEncryptDecryptUtil() throws Exception {
        String text = "李丽璇";
        String encrypt = RsaEncryptDecryptUtil.publicKeyEncrypt(text.getBytes());
        String decrypt = RsaEncryptDecryptUtil.privateKeyDecrypt(encrypt.getBytes());
        System.out.println(decrypt);
    }

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
