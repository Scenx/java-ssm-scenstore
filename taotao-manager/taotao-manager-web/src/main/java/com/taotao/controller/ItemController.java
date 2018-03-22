package com.taotao.controller;

import com.taotao.common.pojo.EUDdataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理表现层
 *
 * @author Scen
 * @date 2018/3/9 20:16
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 测试根据id查询查询商品
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        return itemService.getItemById(itemId);
    }

    /**
     * 查询所有商品
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDdataGridResult getItemList(Integer page, Integer rows) {
        return itemService.getItemList(page, rows);
    }

    /**
     * 保存商品
     * @param item
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult cteateItem(TbItem item, TbItemDesc itemDesc, TbItemParamItem itemParamItem) throws Exception {
        return itemService.createItem(item,itemDesc,itemParamItem);
    }
}
