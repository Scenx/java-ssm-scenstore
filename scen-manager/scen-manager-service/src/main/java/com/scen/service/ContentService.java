package com.scen.service;

import com.scen.pojo.EUDdataGridResult;
import com.scen.pojo.ScenResult;
import com.scen.pojo.TbContent;

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
    ScenResult saveContent(TbContent content);


    /**
     * 根据id批量删除内容
     *
     * @param ids
     * @return
     */
    ScenResult deleteContent(Long[] ids);

    /**
     * 更新内容
     *
     * @param content
     * @return
     */
    ScenResult editContent(TbContent content);
}
