package com.scen.portal.service;

import com.scen.pojo.ItemInfo;

/**
 * 展示商品业务层接口
 *
 * @author Scen
 * @date 2018/4/10 10:51
 */
public interface ItemService {
    /**
     * 根据id查询商品基础信息
     *
     * @param itemId
     * @return
     */
    ItemInfo getItemById(Long itemId);

    /**
     * 根据商品id查询商品描述
     *
     * @param itemId
     * @return
     */
    String getItemDescById(Long itemId);


    /**
     * 根据商品查询商品规格参数
     *
     * @param itemId
     * @return
     */
    String getItemParamItem(Long itemId);
}
