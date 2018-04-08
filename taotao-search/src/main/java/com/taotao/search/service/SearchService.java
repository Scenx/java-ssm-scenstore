package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * 查询商品业务层接口
 *
 * @author Scen
 * @date 2018/4/8 16:00
 */
public interface SearchService {

    /**
     * 条件查询商品
     *
     * @param queryString
     * @param page
     * @param rows
     * @return
     */
    SearchResult search(String queryString, Integer page, Integer rows) throws SolrServerException;
}
