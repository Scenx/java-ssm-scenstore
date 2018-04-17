package com.scen.service;

import com.scen.pojo.EUTreeNode;

import java.util.List;

/**
 * 商品分类管理接口
 *
 * @author Scen
 * @date 2018/3/10 12:29
 */
public interface ItemCatService {
    /**
     * 树形节点列表
     *
     * @param parentId
     * @return
     */
    List<EUTreeNode> getCatList(Long parentId);
}
