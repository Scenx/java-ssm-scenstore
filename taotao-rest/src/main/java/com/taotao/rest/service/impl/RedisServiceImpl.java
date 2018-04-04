package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * redis缓存业务层实现类
 *
 * @author Scen
 * @date 2018/4/4 17:50
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Value("${INDEX_ITEMCAT_REDIS_KEY}")
    private String INDEX_ITEMCAT_REDIS_KEY;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult syncContent(Long contentCid) {
        try {
            jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid + "");
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult syncItemCat() {
        try {
            jedisClient.del(INDEX_ITEMCAT_REDIS_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
