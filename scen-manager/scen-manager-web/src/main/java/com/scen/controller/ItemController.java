package com.scen.controller;

import com.scen.pojo.EUDdataGridResult;
import com.scen.pojo.SolrIf;
import com.scen.pojo.ScenResult;
import com.scen.common.utils.HttpClientUtil;
import com.scen.common.utils.JsonUtils;
import com.scen.pojo.TbItem;
import com.scen.pojo.TbItemDesc;
import com.scen.pojo.TbItemParamItem;
import com.scen.service.ItemDescService;
import com.scen.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理表现层
 *
 * @author Scen
 * @date 2018/3/9 20:16
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Value("${SEARCH_MANAGER_ADD_URL}")
    private String SEARCH_MANAGER_ADD_URL;

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
    public ScenResult cteateItem(TbItem item, TbItemDesc itemDesc, TbItemParamItem itemParamItem) throws Exception {
        ScenResult scenResult = itemService.createItem(item, itemDesc, itemParamItem);
        //        添加的参数
        Map<String, String> param = new HashMap<>();
        param.put("id", scenResult.getData() + "");
        //        调用scen-search的服务同步solr
        try {
            HttpClientUtil.doGet(SEARCH_MANAGER_ADD_URL, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ScenResult.ok();
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
    public ScenResult updateItem(TbItem item, TbItemDesc itemDesc, Long itemParamId, String itemParams) throws Exception {
        ScenResult scenResult = itemService.updateItem(item, itemDesc, itemParamId, itemParams);
        if (item.getStatus() == 1) {
            //        添加的参数
            Map<String, String> param = new HashMap<>();
            param.put("id", item.getId() + "");
            //        调用scen-search的服务同步solr
            try {
                HttpClientUtil.doGet(SEARCH_MANAGER_ADD_URL, param);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return scenResult;
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
    public ScenResult getItemDesc(@PathVariable Long itemId) {
        return itemDescService.getItemDesc(itemId);
    }

    /**
     * 根据id批量删除商品
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ScenResult deleteItem(String ids) {
        List<SolrIf> list = JsonUtils.jsonToList(ids, SolrIf.class);
        return itemService.deleteItem(list);
    }

    /**
     * 根据id批量下架商品
     *
     * @param ids
     * @return
     */
    @RequestMapping("/instock")
    @ResponseBody
    public ScenResult instockItem(String ids) {
        List<SolrIf> list = JsonUtils.jsonToList(ids, SolrIf.class);
        return itemService.instockItem(list);
    }

    /**
     * 根据id批量上架商品
     *
     * @param ids
     * @return
     */
    @RequestMapping("/reshelf")
    @ResponseBody
    public ScenResult reshelfItem(Long[] ids) {
        return itemService.reshelfItem(ids);
    }
}
