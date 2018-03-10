package com.taotao.service;

import com.taotao.common.pojo.EUDdataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * 商品管理业务层接口
 *
 * @author Scen
 * @date 2018/3/9 20:06
 */
public interface ItemService {
    /**
     * 根据id查询商品信息
     *
     * @param itemId
     * @return
     */
    TbItem getItemById(Long itemId);

    /**
     * 查询所有商品并分页
     *
     * @param page
     * @param rows
     * @return
     */
    EUDdataGridResult getItemList(Integer page, Integer rows);

    /**
     * 保存商品
     * @param item
     * @return
     */
    TaotaoResult createItem(TbItem item);
}
