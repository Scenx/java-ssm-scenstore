package com.scen.portal.service.impl;

import com.scen.pojo.SearchResult;
import com.scen.pojo.ScenResult;
import com.scen.common.utils.HttpClientUtil;
import com.scen.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品搜索业务层
 *
 * @author Scen
 * @date 2018/4/8 17:09
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String queryString, Integer page) {
//        查询参数
        Map<String, String> param = new HashMap<>();
        param.put("q", queryString);
        param.put("page", page + "");
        try {
//        调用scen-search的服务
            String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
//        把字符串转换成java对象
            ScenResult scenResult = ScenResult.formatToPojo(json, SearchResult.class);
            if (scenResult.getStatus() == 200) {
                SearchResult result = (SearchResult) scenResult.getData();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
