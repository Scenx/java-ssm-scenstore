package com.scen.rest.controller;

import com.scen.common.pojo.ScenResult;
import com.scen.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品查询服务表现层
 *
 * @author Scen
 * @date 2018/4/10 9:53
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;


    /**
     * 查询商品基础信息
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public ScenResult getItemBaseInfo(@PathVariable Long itemId) {
        return itemService.getItemBaseInfo(itemId);
    }

    /**
     * 查询商品描述信息
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public ScenResult getItemDesc(@PathVariable Long itemId) {
        return itemService.getItemDesc(itemId);
    }

    /**
     * 查询商品规格参数
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public ScenResult getItemParamItem(@PathVariable Long itemId) {
        return itemService.getItemParamItem(itemId);
    }


}
