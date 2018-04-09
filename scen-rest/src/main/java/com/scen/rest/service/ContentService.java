package com.scen.rest.service;

import com.scen.pojo.TbContent;

import java.util.List;

/**
 * 内容服务业务层接口
 *
 * @author Scen
 * @date 2018/4/3 11:17
 */
public interface ContentService {

    /**
     * 根据id取内容分类
     *
     * @param contentCid
     * @return
     */
    List<TbContent> getContentList(Long contentCid);
}
