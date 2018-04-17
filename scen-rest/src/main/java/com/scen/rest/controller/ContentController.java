package com.scen.rest.controller;

import com.scen.pojo.ScenResult;
import com.scen.common.utils.ExceptionUtil;
import com.scen.pojo.TbContent;
import com.scen.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容服务表现层
 *
 * @author Scen
 * @date 2018/4/3 11:21
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;


    /**
     * 根据id取分类内容
     *
     * @param contentCategoryId
     * @return
     */
    @RequestMapping("/list/{contentCategoryId}")
    @ResponseBody
    public ScenResult getContentList(@PathVariable Long contentCategoryId) {
        try {
            List<TbContent> list = contentService.getContentList(contentCategoryId);
            return ScenResult.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
