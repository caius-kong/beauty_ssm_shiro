package com.yingjun.ssm.utils;

import org.apache.commons.collections.map.HashedMap;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by kongyunhui on 16/10/18.
 */
public class RedisTest {
    public static void main(String[] args){
        String redisAddress = "127.0.0.1";
        int redisPort = 6379;
        int redisTimeout = 2000;
        String redisPassword = "kong";

        JedisPool pool = new JedisPool(new JedisPoolConfig(), redisAddress, redisPort, redisTimeout, redisPassword);
        Jedis jedis = pool.getResource();
        // String
        jedis.set("test123", "lulu");
        System.out.println(jedis.get("test123"));
//        // List<String>
//        jedis.rpush("ids", "2","1","3"); // 重复运行,重复push
//        System.out.println(jedis.lrange("ids", 0, -1));
//        // Map<String, String>
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        map.put("name", "kyh");
//        jedis.hmset("user", map);
//        System.out.println(jedis.hmget("user", "id", "name"));
        jedis.close();
    }
}
