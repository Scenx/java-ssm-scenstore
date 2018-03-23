package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDdataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public TaotaoResult createItem(TbItem item, TbItemDesc itemDesc, TbItemParamItem itemParamItem) throws Exception {
//        补全item
//        生成商品ID
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());

//        插入到数据库
        itemMapper.insert(item);
        TaotaoResult result = insertItemDesc(itemId, itemDesc);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        result = insertItemParamItem(itemId, itemParamItem);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        return TaotaoResult.ok();
    }

    /**
     * 添加商品描述
     *
     * @param itemDesc
     * @return
     */
    private TaotaoResult insertItemDesc(Long itemId, TbItemDesc itemDesc) {
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    /**
     * 添加商品规格的具体参数
     *
     * @param itemId
     * @param itemParamItem
     * @return
     */
    private TaotaoResult insertItemParamItem(Long itemId, TbItemParamItem itemParamItem) {
        itemParamItem.setItemId(itemId);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        itemParamItemMapper.insert(itemParamItem);
        return TaotaoResult.ok();
    }
}
