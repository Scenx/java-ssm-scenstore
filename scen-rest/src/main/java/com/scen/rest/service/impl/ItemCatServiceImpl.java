package com.scen.rest.service.impl;

import com.scen.pojo.ScenResult;
import com.scen.common.utils.JsonUtils;
import com.scen.mapper.TbItemCatMapper;
import com.scen.pojo.TbItemCat;
import com.scen.pojo.TbItemCatExample;
import com.scen.rest.dao.JedisClient;
import com.scen.pojo.CatNode;
import com.scen.pojo.CatResult;
import com.scen.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类服务实现类
 *
 * @author Scen
 * @date 2018/4/2 10:17
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_ITEMCAT_REDIS_KEY}")
    private String INDEX_ITEMCAT_REDIS_KEY;

    @Override
    public CatResult getItemCatList() {
//        从缓存中取内容
        try {
            String result = jedisClient.get(INDEX_ITEMCAT_REDIS_KEY);
            if (!StringUtils.isBlank(result)) {
//                把字符串转换成pojo
                CatResult catResult = JsonUtils.jsonToPojo(result, CatResult.class);
                return catResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CatResult catResult = new CatResult();
        //查询分类列表
        catResult.setData(getCatList(0));
//      向缓存中添加内容
        try {
            String cacheString = JsonUtils.objectToJson(catResult);
            jedisClient.set(INDEX_ITEMCAT_REDIS_KEY, cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catResult;
    }

    @Override
    public ScenResult getItemCatById(Long itemCid) {
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(itemCid);
        return ScenResult.ok(itemCat);
    }

    /**
     * 查询分类列表的方法
     *
     * @param parentId
     * @return
     */
    private List<?> getCatList(long parentId) {
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
//        执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
//        返回值list
        List resultList = new ArrayList<>();
//        向list中添加节点
        for (TbItemCat tbItemCat : list) {
//            判断是否为父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                } else {
                    catNode.setName(tbItemCat.getName());
                }

                catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
                catNode.setItem(getCatList(tbItemCat.getId()));
                resultList.add(catNode);
            } else {
//                如果是叶子节点
                resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }

        }
        return resultList;
    }
}
