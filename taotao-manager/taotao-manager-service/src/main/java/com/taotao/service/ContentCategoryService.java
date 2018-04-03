package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

/**
 * 内容分类管理业务层接口
 *
 * @author Scen
 * @date 2018/4/2 15:12
 */
public interface ContentCategoryService {
    /**
     * 获取分类节点
     *
     * @param parentId
     * @return
     */
    List<EUTreeNode> getCategoryList(Long parentId);

    /**
     * 添加分类节点
     *
     * @param parentId
     * @param name
     * @return
     */
    TaotaoResult insertContenCategory(Long parentId, String name);

    /**
     * 删除分类节点
     *
     * @param id
     * @return
     */
    TaotaoResult deleteContenCategory(Long id);


    /**
     * 更新分类节点
     *
     * @param contentCategory
     */
    void updateContentCategory(TbContentCategory contentCategory);
}
