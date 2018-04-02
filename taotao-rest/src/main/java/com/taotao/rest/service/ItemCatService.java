package com.taotao.rest.service;

import com.taotao.rest.pojo.CatResult;

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
}
