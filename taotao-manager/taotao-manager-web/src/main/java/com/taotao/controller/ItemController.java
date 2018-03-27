package com.taotao.controller;

import com.taotao.common.pojo.EUDdataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Autowired
    private ItemDescService itemDescService;


    /**
     * 查询所有商品
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDdataGridResult getItemList(Long id, String title, String catName, Long startPrice, Long endPrice, Integer page, Integer rows) {
        return itemService.getItemList(page, rows, id, title, catName, startPrice, endPrice);
    }

    /**
     * 生成商品报表
     *
     * @param id
     * @param title
     * @param catName
     * @param startPrice
     * @param endPrice
     */
    @RequestMapping("/outputExcel")
    public void outputExcel(HttpServletResponse response, Long id, String title, String catName, Long startPrice, Long endPrice) throws Exception {
        itemService.getExcel(response, id, title, catName, startPrice, endPrice);
    }

    /**
     * 保存商品
     *
     * @param item
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult cteateItem(TbItem item, TbItemDesc itemDesc, TbItemParamItem itemParamItem) throws Exception {
        return itemService.createItem(item, itemDesc, itemParamItem);
    }

    /**
     * 更新商品
     *
     * @param item
     * @param itemDesc
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult updateItem(TbItem item, TbItemDesc itemDesc, Long itemParamId, String itemParams) throws Exception {
        return itemService.updateItem(item, itemDesc, itemParamId, itemParams);
    }

    /**
     * 重写日期绑定接受前台的日期字符串
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }


    /**
     * 根据商品id查询商品详细信息
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDesc(@PathVariable Long itemId) {
        return itemDescService.getItemDesc(itemId);
    }
}
