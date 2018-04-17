package com.scen.search.service.impl;

import com.scen.search.dao.SearchDao;
import com.scen.search.service.SearchService;
import com.scen.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查询商品业务层实现类
 *
 * @author Scen
 * @date 2018/4/8 16:02
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws SolrServerException {
//        创建查询条件
        SolrQuery query = new SolrQuery();
//        设置查询条件
        query.setQuery(queryString);
//        设置分页
        query.setStart((page - 1) * rows);
        query.setRows(rows);
//        设置默认搜索域
        query.set("df", "item_keywords");
//        设置高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
//        执行查询
        SearchResult searchResult = searchDao.search(query);
//        计算查询结果总页数
        Long recordCount = searchResult.getRecordCount();
        Long pageCount = recordCount / rows;
        if (recordCount % rows > 0) {
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        searchResult.setCurPage((long) page);
        return searchResult;
    }
}
