package com.taotao.search.dao;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * 搜索商品持久层接口
 *
 * @author Scen
 * @date 2018/4/8 15:42
 */
public interface SearchDao {

    /**
     * 商品搜索
     *
     * @param query
     * @return
     */
    SearchResult search(SolrQuery query) throws SolrServerException;
}
