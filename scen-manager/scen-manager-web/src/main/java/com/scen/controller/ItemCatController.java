package com.scen.controller;

import com.scen.common.pojo.EUTreeNode;
import com.scen.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理表现层
 *
 * @author Scen
 * @date 2018/3/10 12:42
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 取所有商品分类
     *
     * @param patentId
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    private List<EUTreeNode> getCatList(@RequestParam(value = "id", defaultValue = "0") Long patentId) {
        return itemCatService.getCatList(patentId);
    }
}
