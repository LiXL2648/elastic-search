package com.li.redis.service;

import com.li.redis.util.JedisPoolUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Random;

@Service
public class SecondKillService {

    public String secondKill(String prodId) {

        // 判断是否为空
        if (prodId == null || prodId.isEmpty()) {
            System.out.println("请输入产品编号");
            return "请输入产品编号";
        }

        // 获取用户ID
        String userId = new Random().nextInt(100000) + "";

        // 产品key和用户key
        String prodKey = "sk:" + prodId + ":num";
        String userKey = "sk:" + prodId + ":user";

        // 连接redis
        try (Jedis jedis = new Jedis("10.10.0.26", 6379)) {

            // 判断秒杀产品是否存在
            String prodValue = jedis.get(prodKey);
            if (prodValue == null) {
                System.out.println("秒杀未开始");
                return "秒杀未开始";
            }

            // 判断秒杀是否结束
            if (Integer.parseInt(jedis.get(prodKey)) <= 0) {
                System.out.println("秒杀已结束");
                return "秒杀已结束";
            }

            // 判断用户是否重复参与秒杀
            if (jedis.sismember(userKey, userId)) {
                System.out.println("不能重复参与秒杀");
                return "不能重复参与秒杀";
            }

            // 秒杀过程
            // 监视库存
            jedis.watch(prodKey);

            // 开启事务
            Transaction multi = jedis.multi();

            // 组队操作
            multi.decr(prodKey);
            multi.sadd(userKey, userId);

            // 执行
            List<Object> exec = multi.exec();

            if (exec == null || exec.size() == 0) {
                System.out.println("秒杀失败");
                return "秒杀失败";
            }

            System.out.println("秒杀成功");
            return "秒杀成功";
        }
    }

    public String secondKillByScript(String prodId) {
        // 判断是否为空
        if (prodId == null || prodId.isEmpty()) {
            System.out.println("请输入产品编号");
            return "请输入产品编号";
        }

        // 获取用户ID
        String userId = new Random().nextInt(100000) + "";

        // 连接redis
        try (Jedis jedis = JedisPoolUtil.getJedisPool().getResource()) {

            // 编写 LUA 脚本
            String script = "local userId = KEYS[1];\n" +
                    "local prodId = KEYS[2];\n" +
                    "local prodKey = \"sk:\"..prodId..\":num\";\n" +
                    "local userKey = \"sk:\"..prodId..\":user\";\n" +
                    "local prodNum = redis.call(\"get\", prodKey);\n" +
                    "if prodNum == false then\n" +
                    "\treturn 0;\n" +
                    "end\n" +
                    "local userExists = redis.call(\"sismember\", userKey, userId);\n" +
                    "if tonumber(userExists) == 1 then\n" +
                    "\treturn 3;\n" +
                    "end\n" +
                    "if tonumber(prodNum) <= 0 then\n" +
                    "\treturn 2;\n" +
                    "else\n" +
                    "\tredis.call(\"decr\", prodKey);\n" +
                    "\tredis.call(\"sadd\", userKey, userId);\n" +
                    "end\n" +
                    "return 1;";

            // 加载 LUA 脚本
            String sha1 = jedis.scriptLoad(script);

            // 执行 LUA 脚本
            Object evalsha = jedis.evalsha(sha1, 2, userId, prodId);
            int result = Integer.parseInt(evalsha.toString());
            if (result == 0) {
                System.out.println("秒杀未开始");
                return "秒杀未开始";
            } else if (result == 2) {
                System.out.println("秒杀已结束");
                return "秒杀已结束";
            } else if (result == 3) {
                System.out.println("不能重复参与秒杀");
                return "不能重复参与秒杀";
            } else if (result == 1) {
                System.out.println("秒杀成功");
                return "秒杀成功";
            } else {
                System.out.println("秒杀失败");
                return "秒杀失败";
            }
        }
    }
}
