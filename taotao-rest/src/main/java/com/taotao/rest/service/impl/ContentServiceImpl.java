package com.taotao.rest.service.impl;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容服务业务层实现类
 *
 * @author Scen
 * @date 2018/4/3 11:18
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;


    @Override
    public List<TbContent> getContentList(Long contentCid) {
//        根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
//        执行查询
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);

        return list;
    }
}
