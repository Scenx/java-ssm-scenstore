package com.scen.search.controller;

import com.scen.pojo.ScenResult;
import com.scen.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 索引库维护表现层
 *
 * @author Scen
 * @date 2018/4/8 14:54
 */
@Controller
@RequestMapping("/manager")
public class ItemController {

    @Autowired
    private ItemService itemService;


    /**
     * 导入商品数据到索引库
     *
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @RequestMapping("/importall")
    @ResponseBody
    public ScenResult importAllItems() throws IOException, SolrServerException {
        return itemService.importAllItems();
    }

    /**
     * 同步商品到索引库（添加和修改）
     *
     * @param id
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ScenResult add(String id) {
        return itemService.add(id);
    }

    /**
     * 同步商品到索引库（删除）
     *
     * @param id
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public ScenResult del(String id) {
        return itemService.del(id);
    }
}
