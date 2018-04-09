package com.scen.rest.dao;

/**
 * 单机版redis客户端接口
 *
 * @author Scen
 * @date 2018/4/4 15:38
 */
public interface JedisClient {
    /**
     * 取值
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置值
     *
     * @param key
     * @param value
     * @return
     */
    String set(String key, String value);


    /**
     * 哈希取值
     *
     * @param hkey
     * @param key
     * @return
     */
    String hget(String hkey, String key);


    /**
     * 哈希设置值
     *
     * @param hkey
     * @param key
     * @param value
     * @return
     */
    Long hset(String hkey, String key, String value);

    /**
     * 自动增长
     *
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 值设置过期时间
     *
     * @param key
     * @param second
     * @return
     */
    Long expire(String key, Integer second);

    /**
     * 查看是否过期还剩多久
     *
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * 根据key删除值
     *
     * @param key
     * @return
     */
    Long del(String key);

    /**
     * 删除哈希值
     *
     * @param hkey
     * @param key
     * @return
     */
    Long hdel(String hkey, String key);
}
