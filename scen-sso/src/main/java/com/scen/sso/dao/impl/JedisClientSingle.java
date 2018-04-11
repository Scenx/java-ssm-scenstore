package com.scen.sso.dao.impl;

import com.scen.sso.dao.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 单机版实现类
 *
 * @author Scen
 * @date 2018/4/4 16:10
 */
public class JedisClientSingle implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get(key);
        jedis.close();
        return s;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.set(key, value);
        jedis.close();
        return s;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.hget(hkey, key);
        jedis.close();
        return s;
    }

    @Override
    public Long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long l = jedis.hset(hkey, key, value);
        jedis.close();
        return l;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long l = jedis.incr(key);
        jedis.close();
        return l;
    }

    @Override
    public Long expire(String key, Integer second) {
        Jedis jedis = jedisPool.getResource();
        Long l = jedis.expire(key, second);
        jedis.close();
        return l;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long l = jedis.ttl(key);
        jedis.close();
        return l;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long l = jedis.del(key);
        jedis.close();
        return l;
    }

    @Override
    public Long hdel(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        Long l = jedis.hdel(hkey, key);
        jedis.close();
        return l;
    }
}
