package com.scen.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scen.common.pojo.EUDdataGridResult;
import com.scen.common.pojo.ScenResult;
import com.scen.common.utils.HttpClientUtil;
import com.scen.mapper.TbContentMapper;
import com.scen.pojo.TbContent;
import com.scen.pojo.TbContentExample;
import com.scen.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

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
    public ScenResult saveContent(TbContent content) {
//        补全内容
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
//        添加缓存同步逻辑
        try {
            HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ScenResult.ok();
    }

    @Override
    public ScenResult deleteContent(Long[] ids) {
        for (Long id : ids) {
            TbContent content = contentMapper.selectByPrimaryKey(id);
            contentMapper.deleteByPrimaryKey(id);
            try {
                HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ScenResult.ok();
    }

    @Override
    public ScenResult editContent(TbContent content) {
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(content);
        try {
            HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ScenResult.ok();
    }
}
