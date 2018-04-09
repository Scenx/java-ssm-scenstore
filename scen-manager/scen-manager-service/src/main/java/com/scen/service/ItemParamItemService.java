package com.scen.service;

import com.scen.common.pojo.ScenResult;

/**
 * 商品具体规格参数业务层接口
 * @author Scen
 * @date 2018/3/22 13:40
 */
public interface ItemParamItemService {
    /**
     * 根据id查询商品规格参数
     * @param itemId
     * @return
     */
    ScenResult getItemParamByItemId(Long itemId);
}
