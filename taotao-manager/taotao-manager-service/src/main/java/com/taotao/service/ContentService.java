package com.taotao.service;

import com.taotao.common.pojo.EUDdataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * 内容管理业务层接口
 *
 * @author Scen
 * @date 2018/4/3 8:57
 */
public interface ContentService {

    /**
     * 查询指定分类的所有内容
     *
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    EUDdataGridResult getContentList(Integer page, Integer rows, Long categoryId);

    /**
     * 保存内容
     *
     * @param content
     * @return
     */
    TaotaoResult saveContent(TbContent content);


    /**
     * 根据id批量删除内容
     *
     * @param ids
     * @return
     */
    TaotaoResult deleteContent(Long[] ids);

    /**
     * 更新内容
     *
     * @param content
     * @return
     */
    TaotaoResult editContent(TbContent content);
}
