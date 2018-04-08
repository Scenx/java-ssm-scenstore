package com.taotao.rest.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Scen
 * @date 2018/4/8 12:53
 */
public class SolrJTest {

    @Test
    public void addDocument() throws IOException, SolrServerException {
//        创建链接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8082/solr");
//        创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test001");
        document.addField("item_title", "测试商品2");
        document.addField("item_price", 1000);
//        把文档写入索引库
        solrServer.add(document);
//        提交
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws IOException, SolrServerException {
        //        创建链接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8082/solr");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }
}
