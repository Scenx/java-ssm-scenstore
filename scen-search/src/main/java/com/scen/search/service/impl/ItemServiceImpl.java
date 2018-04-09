package com.scen.search.service.impl;

import com.scen.common.pojo.Item;
import com.scen.common.pojo.ScenResult;
import com.scen.common.utils.ExceptionUtil;
import com.scen.search.mapper.ItemMapper;
import com.scen.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * solr检索商品服务层实现类
 *
 * @author Scen
 * @date 2018/4/8 14:16
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public ScenResult importAllItems() {
        try {
//        查询商品列表
            List<Item> list = itemMapper.getItemList();
//        把信息写入索引库
            for (Item item : list) {
                //            创建一个SolrInputDocument对象
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", item.getId());
                document.setField("item_title", item.getTitle());
                document.setField("item_sell_point", item.getSell_point());
                document.setField("item_price", item.getPrice());
                document.setField("item_image", item.getImage());
                document.setField("item_category_name", item.getCategory_name());
                document.setField("item_desc", item.getItem_desc());
                solrServer.add(document);
            }
//        提交修改
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return ScenResult.ok();
    }

    @Override
    public ScenResult add(String id) {
        try {
//        根据id查询数据库商品取出索引库所需字段
            Item item = itemMapper.getItem(id);
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id", item.getId());
            document.setField("item_title", item.getTitle());
            document.setField("item_sell_point", item.getSell_point());
            document.setField("item_price", item.getPrice());
            document.setField("item_image", item.getImage());
            document.setField("item_category_name", item.getCategory_name());
            document.setField("item_desc", item.getItem_desc());
            solrServer.add(document);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return ScenResult.ok();
    }

    @Override
    public ScenResult del(String id) {
        try {
            solrServer.deleteById(id);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return ScenResult.ok();
    }

}
