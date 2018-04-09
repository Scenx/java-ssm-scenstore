package com.scen.search.service;

import com.scen.common.pojo.SearchResult;
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
     * @param queryString
     * @param page
     * @param rows
     * @return 条件查询商品
     * @throws SolrServerException
     */
    SearchResult search(String queryString, Integer page, Integer rows) throws SolrServerException;
}
