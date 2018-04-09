package com.scen.service;

import com.scen.common.pojo.EUDdataGridResult;
import com.scen.common.pojo.SolrIf;
import com.scen.common.pojo.ScenResult;
import com.scen.pojo.TbItem;
import com.scen.pojo.TbItemDesc;
import com.scen.pojo.TbItemParamItem;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * @param id
     * @param title
     * @param catName
     * @param startPrice
     * @param endPrice
     * @return
     */
    EUDdataGridResult getItemList(Integer page, Integer rows, Long id, String title, String catName, Long startPrice, Long endPrice);

    /**
     * 保存商品
     * @param item
     * @param itemDesc
     * @param itemParamItem
     * @return
     * @throws Exception
     */
    ScenResult createItem(TbItem item, TbItemDesc itemDesc, TbItemParamItem itemParamItem) throws Exception;

    /**
     * 生成商品报表
     *
     * @param response
     * @param id
     * @param title
     * @param catName
     * @param startPrice
     * @param endPrice
     * @throws Exception
     */
    void getExcel(HttpServletResponse response, Long id, String title, String catName, Long startPrice, Long endPrice) throws Exception;

    /**
     * 更新商品
     *
     * @param item
     * @param itemDesc
     * @param itemParamId
     * @param itemParams
     * @return
     * @throws Exception
     */
    ScenResult updateItem(TbItem item, TbItemDesc itemDesc, Long itemParamId, String itemParams) throws Exception;

    /**
     * 根据id批量删除商品
     *
     * @param list
     * @return
     */
    ScenResult deleteItem(List<SolrIf> list);

    /**
     * 根据id批量下架商品
     *
     * @param list
     * @return
     */
    ScenResult instockItem(List<SolrIf> list);

    /**
     * 根据id批量上架商品
     *
     * @param ids
     * @return
     */
    ScenResult reshelfItem(Long[] ids);
}
