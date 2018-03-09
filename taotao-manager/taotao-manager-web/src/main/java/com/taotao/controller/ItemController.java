package com.taotao.controller;

import com.taotao.common.pojo.EUDdataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理表现层
 *
 * @author Scen
 * @date 2018/3/9 20:16
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 测试根据id查询查询商品
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        return itemService.getItemById(itemId);
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EUDdataGridResult getItemList(Integer page, Integer rows) {
        return itemService.getItemList(page, rows);
    }
}
