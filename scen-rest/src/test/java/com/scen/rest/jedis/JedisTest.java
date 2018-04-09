package com.scen.rest.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;

/**
 * Jedis单机版客户端测试
 *
 * @author Scen
 * @date 2018/4/4 13:54
 */
public class JedisTest {

    @Test
    public void testJedisSingle() {
//        创建一个jedis对象
        Jedis jedis = new Jedis("192.168.1.147", 6379);
//        直接调用jedis对象方法，方法名称和redis命令一致
        jedis.set("key1", "jedisTest");
        String string = jedis.get("key1");
        System.out.println(string);
//        关闭jedis链接
        jedis.close();

    }

    @Test
    public void testJedisCluster() throws IOException {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.1.147", 7001));
        nodes.add(new HostAndPort("192.168.1.147", 7002));
        nodes.add(new HostAndPort("192.168.1.147", 7003));
        nodes.add(new HostAndPort("192.168.1.147", 7004));
        nodes.add(new HostAndPort("192.168.1.147", 7005));
        nodes.add(new HostAndPort("192.168.1.147", 7006));
        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("key1", "1000");
        String key1 = cluster.get("key1");
        System.out.println(key1);
        cluster.close();
    }


}
