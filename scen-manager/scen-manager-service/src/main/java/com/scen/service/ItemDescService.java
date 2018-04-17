package com.scen.service;

import com.scen.pojo.ScenResult;

/**
 * @author Scen
 * @date 2018/3/23 15:25
 */
public interface ItemDescService {

    /**
     * 根据商品id查询商品详情
     *
     * @param itemId
     * @return
     */
    ScenResult getItemDesc(Long itemId);
}
