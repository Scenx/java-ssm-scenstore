package com.scen.service.impl;

import com.scen.common.pojo.ScenResult;
import com.scen.mapper.TbItemDescMapper;
import com.scen.pojo.TbItemDesc;
import com.scen.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Scen
 * @date 2018/3/23 15:28
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Override
    public ScenResult getItemDesc(Long itemId) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        return ScenResult.ok(itemDesc);
    }
}
