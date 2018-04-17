package com.scen.controller;

import com.scen.pojo.EUDdataGridResult;
import com.scen.pojo.ScenResult;
import com.scen.pojo.TbContent;
import com.scen.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 内容管理表现层
 *
 * @author Scen
 * @date 2018/4/3 9:04
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;


    /**
     * 查询指定分类的所有内容
     *
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    @RequestMapping("/query/list")
    @ResponseBody
    public EUDdataGridResult getContentList(Integer page, Integer rows, Long categoryId) {
        return contentService.getContentList(page, rows, categoryId);
    }

    /**
     * 保存内容
     *
     * @param content
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ScenResult saveContent(TbContent content) {
        return contentService.saveContent(content);
    }


    /**
     * 根据id批量删除内容
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ScenResult deleteContent(Long[] ids) {
        return contentService.deleteContent(ids);
    }

    /**
     * 更新内容
     *
     * @param content
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ScenResult editContent(TbContent content) {
        return contentService.editContent(content);
    }
}
