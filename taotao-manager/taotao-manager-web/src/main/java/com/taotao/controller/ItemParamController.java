package com.taotao.controller;

import com.taotao.common.pojo.EUDdataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品规格参数表现层
 *
 * @author Scen
 * @date 2018/3/22 10:50
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    /**
     * 根据id查询商品规格参数
     *
     * @param itemCatId
     * @return
     */
    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
        return itemParamService.getItemParamByCid(itemCatId);
    }

    /**
     * 保存商品规格
     *
     * @param cid
     * @param paramData
     * @return
     */
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData) {
//        创建pojo对象
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        return itemParamService.insertItemParam(itemParam);
    }

    /**
     * 查询所有类目的规格模版
     *
     * @param catName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDdataGridResult getItemParamList(String catName, Integer page, Integer rows) {
        return itemParamService.getItemParamList(page, rows, catName);
    }

    /**
     * 修改指定类目的规格
     *
     * @param itemParam
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateItemParam(TbItemParam itemParam) {
        return itemParamService.updateItemParam(itemParam);
    }

    /**
     * 根据id批量删除类目规格
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteItemParam(Long[] ids) {
        return itemParamService.deleteItemParam(ids);
    }
}
