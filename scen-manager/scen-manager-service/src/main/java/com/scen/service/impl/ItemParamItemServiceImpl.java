package com.scen.service.impl;

import com.scen.common.pojo.ScenResult;
import com.scen.mapper.TbItemParamItemMapper;
import com.scen.pojo.TbItemParamItem;
import com.scen.pojo.TbItemParamItemExample;
import com.scen.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 展示商品规格参数实现类
 * @author Scen
 * @date 2018/3/22 13:41
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public ScenResult getItemParamByItemId(Long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list == null || list.size() == 0) {
            return ScenResult.ok();
        }
        TbItemParamItem itemParamItem = list.get(0);
        return ScenResult.ok(itemParamItem);
    }
}
