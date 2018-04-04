package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * redis缓存业务层接口
 *
 * @author Scen
 * @date 2018/4/4 17:49
 */
public interface RedisService {

    /**
     * 同步内容
     *
     * @param contentCid
     * @return
     */
    TaotaoResult syncContent(Long contentCid);

    /**
     * 同步全部商品分类
     *
     * @return
     */
    TaotaoResult syncItemCat();
}
