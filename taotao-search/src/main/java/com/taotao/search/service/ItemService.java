package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * solr检索商品服务层接口
 *
 * @author Scen
 * @date 2018/4/8 14:15
 */
public interface ItemService {

    /**
     * 导入检索出来的商品到solr服务器
     * @return 状态
     * @throws IOException
     * @throws SolrServerException
     */
    TaotaoResult importAllItems() throws IOException, SolrServerException;

    /**
     * 添加和修改商品到索引库
     *
     * @param id
     * @return
     */
    TaotaoResult add(String id);

    /**
     * 同步商品到索引库（删除）
     *
     * @param id
     * @return
     */
    TaotaoResult del(String id);
}
