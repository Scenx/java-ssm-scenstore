package com.scen.search.controller;

import com.scen.pojo.ScenResult;
import com.scen.common.utils.ExceptionUtil;
import com.scen.pojo.SearchResult;
import com.scen.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 搜索商品表现层
 *
 * @author Scen
 * @date 2018/4/8 16:20
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 商品搜索服务
     *
     * @param queryString
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public ScenResult search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "60") Integer rows) {
//        查询条件不能为空
        if (StringUtils.isBlank(queryString)) {
            return ScenResult.build(400, "查询条件不能为空");
        }
        SearchResult searchResult = null;
        try {
            searchResult = searchService.search(queryString, page, rows);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return ScenResult.ok(searchResult);
    }
}
