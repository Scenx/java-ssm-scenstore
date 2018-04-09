package com.scen.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scen.common.pojo.EUDdataGridResult;
import com.scen.common.pojo.SolrIf;
import com.scen.common.pojo.ScenResult;
import com.scen.common.utils.ExcelUtil;
import com.scen.common.utils.HttpClientUtil;
import com.scen.common.utils.IDUtils;
import com.scen.mapper.TbItemDescMapper;
import com.scen.mapper.TbItemMapper;
import com.scen.mapper.TbItemParamItemMapper;
import com.scen.pojo.ItemBean;
import com.scen.pojo.TbItem;
import com.scen.pojo.TbItemDesc;
import com.scen.pojo.TbItemParamItem;
import com.scen.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 商品管理业务层实现类
 *
 * @author Scen
 * @date 2018/3/9 20:09
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Value("${SEARCH_MANAGER_DEL_URL}")
    private String SEARCH_MANAGER_DEL_URL;

    @Value("${SEARCH_MANAGER_ADD_URL}")
    private String SEARCH_MANAGER_ADD_URL;

    @Override
    public TbItem getItemById(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EUDdataGridResult getItemList(Integer page, Integer rows, Long id, String title, String catName, Long startPrice, Long endPrice) {

        Map<String, Object> map = new HashMap<String, Object>();
        //检索条件封装
        if (id != null) {
            map.put("id", id);
        }
        if (StringUtils.isNotBlank(title)) {
            map.put("title", title);
        }
        if (StringUtils.isNotBlank(catName)) {
            map.put("catName", catName);
        }
        if (startPrice != null && endPrice == null) {
            map.put("startPrice", startPrice);
        }
        if (endPrice != null && startPrice == null) {
            map.put("endPrice", endPrice);
        }
        if (startPrice != null && endPrice != null) {
            map.put("startPrice", startPrice);
            map.put("endPrice", endPrice);
        }

//        查询商品列表
//        分页处理
        PageHelper.startPage(page, rows);
        List<ItemBean> list = itemMapper.getSearchItemList(map);
//        创建一个返回值对象
        EUDdataGridResult result = new EUDdataGridResult();
        result.setRows(list);
//        取记录总条数
        PageInfo<ItemBean> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public ScenResult createItem(TbItem item, TbItemDesc itemDesc, TbItemParamItem itemParamItem) throws Exception {
//        补全item
//        生成商品ID
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());

//        插入到数据库
        itemMapper.insert(item);
        ScenResult result = insertItemDesc(itemId, itemDesc);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        result = insertItemParamItem(itemId, itemParamItem);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        return ScenResult.ok(item.getId());
    }

    @Override
    public void getExcel(HttpServletResponse response, Long id, String title, String catName, Long startPrice, Long endPrice) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //检索条件封装
        if (id != null) {
            map.put("id", id);
        }
        if (StringUtils.isNotBlank(title)) {
            map.put("title", title);
        }
        if (StringUtils.isNotBlank(catName)) {
            map.put("catName", catName);
        }
        if (startPrice != null && endPrice == null) {
            map.put("startPrice", startPrice);
        }
        if (endPrice != null && startPrice == null) {
            map.put("endPrice", endPrice);
        }
        if (startPrice != null && endPrice != null) {
            map.put("startPrice", startPrice);
            map.put("endPrice", endPrice);
        }
        List<ItemBean> list = itemMapper.getSearchItemList(map);
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
        fieldMap.put("id", "商品id");
        fieldMap.put("title", "商品标题");
        fieldMap.put("catName", "商品分类");
        fieldMap.put("sellPoint", "卖点");
        fieldMap.put("price", "价格");
        fieldMap.put("num", "库存");
        fieldMap.put("barcode", "条形码");
        fieldMap.put("statusName", "状态");
        fieldMap.put("created", "创建时间");
        fieldMap.put("updated", "更新时间");
        ExcelUtil.listToExcel(list, fieldMap, "商品报表", "商品报表", response);
    }

    @Override
    public ScenResult updateItem(TbItem item, TbItemDesc itemDesc, Long itemParamId, String itemParams) throws Exception {
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKey(item);
        ScenResult result = updateItemDesc(item.getId(), itemDesc);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        result = updateItemParamItem(item.getCreated(), item.getId(), itemParamId, itemParams);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        return ScenResult.ok();
    }

    @Override
    public ScenResult deleteItem(List<SolrIf> list) {
        for (SolrIf solrIf : list) {
            itemMapper.deleteByPrimaryKey(solrIf.getId());
        }
        for (SolrIf solrIf : list) {
            if (solrIf.getStatus() == 1) {
                //        添加的参数
                Map<String, String> param = new HashMap<>();
                param.put("id", solrIf.getId() + "");
                //        调用scen-search的服务同步solr
                try {
                    HttpClientUtil.doGet(SEARCH_MANAGER_DEL_URL, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ScenResult.ok();
    }

    @Override
    public ScenResult instockItem(List<SolrIf> list) {
        TbItem item = new TbItem();
        for (SolrIf solrIf : list) {
            item.setId(solrIf.getId());
            item.setStatus((byte) 2);
            itemMapper.updateByPrimaryKeySelective(item);
            if (solrIf.getStatus() == 1) {
                //        添加的参数
                Map<String, String> param = new HashMap<>();
                param.put("id", solrIf.getId() + "");
                //        调用scen-search的服务同步solr
                try {
                    HttpClientUtil.doGet(SEARCH_MANAGER_DEL_URL, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return ScenResult.ok();
    }

    @Override
    public ScenResult reshelfItem(Long[] ids) {
        TbItem item = new TbItem();
        for (Long id : ids) {
            item.setId(id);
            item.setStatus((byte) 1);
            itemMapper.updateByPrimaryKeySelective(item);
            //        添加的参数
            Map<String, String> param = new HashMap<>();
            param.put("id", id + "");
            //        调用scen-search的服务同步solr
            try {
                HttpClientUtil.doGet(SEARCH_MANAGER_ADD_URL, param);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ScenResult.ok();
    }

    /**
     * 添加商品描述
     *
     * @param itemDesc
     * @return
     */
    private ScenResult insertItemDesc(Long itemId, TbItemDesc itemDesc) {
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return ScenResult.ok();
    }

    /**
     * 添加商品规格的具体参数
     *
     * @param itemId
     * @param itemParamItem
     * @return
     */
    private ScenResult insertItemParamItem(Long itemId, TbItemParamItem itemParamItem) {
        itemParamItem.setItemId(itemId);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        itemParamItemMapper.insert(itemParamItem);
        return ScenResult.ok();
    }

    /**
     * 更新商品描述
     *
     * @param itemId
     * @param itemDesc
     * @return
     */
    private ScenResult updateItemDesc(Long itemId, TbItemDesc itemDesc) {
        itemDesc.setItemId(itemId);
        itemDesc.setUpdated(new Date());
        itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
        return ScenResult.ok();
    }


    /**
     * 更新商品规格参数
     *
     * @param itemId
     * @param id
     * @param paramData
     * @return
     */
    private ScenResult updateItemParamItem(Date created, Long itemId, Long id, String paramData) {
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setId(id);
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(paramData);
        itemParamItem.setCreated(created);
        itemParamItem.setUpdated(new Date());
        itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);
        return ScenResult.ok();
    }
}
