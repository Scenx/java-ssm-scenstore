package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDdataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.ItemParamBean;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品规格参数业务层实现类
 *
 * @author Scen
 * @date 2018/3/22 10:46
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public TaotaoResult getItemParamByCid(Long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
//        判断是否查询到结果
        if (list != null && list.size() > 0) {
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
//        补全数据
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
//        插入表
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    @Override
    public EUDdataGridResult getItemParamList(Integer page, Integer rows, String catName) {
        Map<String, Object> map = new HashMap<String, Object>();
        //检索条件封装
        if (StringUtils.isNotBlank(catName)) {
            map.put("catName", catName);
        }
        PageHelper.startPage(page, rows);
        List<ItemParamBean> list = itemParamMapper.getItemParamList(map);
        //创建一个返回值对象
        EUDdataGridResult result = new EUDdataGridResult();
        result.setRows(list);
//        取记录总条数
        PageInfo<ItemParamBean> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult updateItemParam(TbItemParam itemParam) {
        itemParamMapper.updateByPrimaryKeySelective(itemParam);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteItemParam(Long[] ids) {
        for (Long id : ids) {
            itemParamMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();
    }
}
