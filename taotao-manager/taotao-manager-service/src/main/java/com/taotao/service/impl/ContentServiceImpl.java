package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDdataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 内容管理业务层实现类
 *
 * @author Scen
 * @date 2018/4/3 9:00
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public EUDdataGridResult getContentList(Integer page, Integer rows, Long categoryId) {
        //        查询商品列表
//        分页处理
        PageHelper.startPage(page, rows);
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
//        创建一个返回值对象
        EUDdataGridResult result = new EUDdataGridResult();
        result.setRows(list);
//        取记录总条数
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult saveContent(TbContent content) {
//        补全内容
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(Long[] ids) {
        for (Long id : ids) {
            contentMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult editContent(TbContent content) {
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(content);
        return TaotaoResult.ok();
    }
}
