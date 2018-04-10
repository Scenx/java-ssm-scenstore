package com.scen.rest.service.impl;

import com.scen.common.pojo.ScenResult;
import com.scen.common.utils.JsonUtils;
import com.scen.mapper.TbItemDescMapper;
import com.scen.mapper.TbItemMapper;
import com.scen.mapper.TbItemParamItemMapper;
import com.scen.pojo.TbItem;
import com.scen.pojo.TbItemDesc;
import com.scen.pojo.TbItemParamItem;
import com.scen.pojo.TbItemParamItemExample;
import com.scen.rest.dao.JedisClient;
import com.scen.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品查询服务业务层实现类
 *
 * @author Scen
 * @date 2018/4/10 9:04
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private JedisClient jedisClient;


    @Override
    public ScenResult getItemBaseInfo(Long itemId) {
        try {
//        添加缓存逻辑
//        从缓存中取商品信息，是商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
//            判断是否幼稚
            if (!StringUtils.isBlank(json)) {
//                把json转换成java对象
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return ScenResult.ok(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        根据商品id查询商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        try {
//        把商品信息写入缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
//        设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //        使用ScenResult包装一下

        return ScenResult.ok(item);
    }

    @Override
    public ScenResult getItemDesc(Long itemId) {
        try {
//        添加缓存逻辑
//        从缓存中取商品信息，是商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
//            判断是否幼稚
            if (!StringUtils.isBlank(json)) {
//                把json转换成java对象
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return ScenResult.ok(itemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        创建查询条件
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
//        把商品信息写入缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
//        设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //        使用ScenResult包装一下
        return ScenResult.ok(itemDesc);
    }

    @Override
    public ScenResult getItemParamItem(Long itemId) {
        try {
//        添加缓存逻辑
//        从缓存中取规格参数
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
//            判断是否幼稚
            if (!StringUtils.isBlank(json)) {
//                把json转换成java对象
                TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return ScenResult.ok(itemParamItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        根据商品id查询规格参数
//        设置查询条件
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            TbItemParamItem itemParamItem = list.get(0);
            try {
//        把商品信息写入缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(itemParamItem));
//        设置key的有效期
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ScenResult.ok(itemParamItem);
        }
        return ScenResult.build(400, "无此商品规格");
    }
}
