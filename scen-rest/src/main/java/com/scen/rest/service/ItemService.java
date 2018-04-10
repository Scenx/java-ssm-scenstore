package com.scen.rest.service;

import com.scen.common.pojo.ScenResult;

/**
 * 商品查询服务业务层接口
 *
 * @author Scen
 * @date 2018/4/10 9:03
 */
public interface ItemService {

    /**
     * 查询商品基础信息
     *
     * @param itemId
     * @return
     */
    ScenResult getItemBaseInfo(Long itemId);

    /**
     * 查询商品描述
     *
     * @param itemId
     * @return
     */
    ScenResult getItemDesc(Long itemId);

    /**
     * 查询商品规格参数
     *
     * @param itemId
     * @return
     */
    ScenResult getItemParamItem(Long itemId);
}
