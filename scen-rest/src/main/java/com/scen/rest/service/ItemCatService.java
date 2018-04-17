package com.scen.rest.service;

import com.scen.pojo.ScenResult;
import com.scen.pojo.CatResult;

/**
 * 商品分类返回json业务层接口
 *
 * @author Scen
 * @date 2018/4/2 10:16
 */
public interface ItemCatService {
    /**
     * 返回商品分类json
     *
     * @return
     */
    CatResult getItemCatList();

    /**
     * 根据id返回商品类别
     *
     * @param itemCid
     * @return
     */
    ScenResult getItemCatById(Long itemCid);
}
