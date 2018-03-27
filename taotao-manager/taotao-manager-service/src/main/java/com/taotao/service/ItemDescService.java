package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;

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
    TaotaoResult getItemDesc(Long itemId);
}
