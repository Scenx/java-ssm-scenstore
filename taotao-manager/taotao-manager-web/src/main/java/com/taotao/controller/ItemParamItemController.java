package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 展示商品规格参数表现层
 *
 * @author Scen
 * @date 2018/3/22 14:00
 */
@Controller
@RequestMapping("/item/param/item")
public class ItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;

    /**
     * 根据查询商品具体规格参数
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/query/{itemId}")
    @ResponseBody
    public TaotaoResult getParamData(@PathVariable Long itemId) {
        return itemParamItemService.getItemParamByItemId(itemId);
    }

}
