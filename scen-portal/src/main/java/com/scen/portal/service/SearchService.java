package com.scen.portal.service;

import com.scen.pojo.SearchResult;

/**
 * 商品搜索业务层接口
 *
 * @author Scen
 * @date 2018/4/8 17:08
 */
public interface SearchService {

    /**
     * 搜索商品
     *
     * @param queryString
     * @param page
     * @return
     */
    SearchResult search(String queryString, Integer page);
}
