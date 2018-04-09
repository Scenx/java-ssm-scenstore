package com.taotao.search.mapper;

import com.taotao.common.pojo.Item;

import java.util.List;

/**
 * solr检索所需商品持久层
 *
 * @author Scen
 * @date 2018/4/8 13:53
 */
public interface ItemMapper {

    /**
     * 检索所有solr所需的商品字段
     *
     * @return
     */
    List<Item> getItemList();

    /**
     * 根据id取商品索引库所需字段
     *
     * @param id
     * @return
     */
    Item getItem(String id);
}
