package com.taotao.service.impl;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理业务层实现类
 *
 * @author Scen
 * @date 2018/4/2 15:15
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(Long parentId) {
//        根据parentId查询节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
//        执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
//            创建一个节点
            EUTreeNode node = new EUTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            resultList.add(node);

        }
        return resultList;
    }

    @Override
    public TaotaoResult insertContenCategory(Long parentId, String name) {
//        创建一个pojo
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategory.setIsParent(false);
        contentCategory.setStatus(1);
        contentCategory.setParentId(parentId);
        contentCategory.setSortOrder(1);
//        添加记录
        contentCategoryMapper.insert(contentCategory);
//        查看父节点的isParent列是否为true,如果不是true改为true
        TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentCat.getIsParent()) {
            parentCat.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentCat);
        }
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult deleteContenCategory(Long id) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        if (contentCategory.getIsParent()) {
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            contentCategoryMapper.deleteByExample(example);
        }
        contentCategoryMapper.deleteByPrimaryKey(id);
        return TaotaoResult.ok();
    }

    @Override
    public void updateContentCategory(TbContentCategory contentCategory) {
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
    }

}
