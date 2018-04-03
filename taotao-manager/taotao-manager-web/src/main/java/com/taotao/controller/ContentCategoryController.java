package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理表现层
 *
 * @author Scen
 * @date 2018/4/2 15:29
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;


    /**
     * 查询分类节点
     *
     * @param parentId
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getCategoryList(parentId);
    }

    /**
     * 添加分类节点
     *
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createContentCategory(Long parentId, String name) {
        return contentCategoryService.insertContenCategory(parentId, name);
    }


    /**
     * 删除内容分类节点
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id) {
        return contentCategoryService.deleteContenCategory(id);
    }


    /**
     * 更新分类节点
     *
     * @param contentCategory
     */
    @RequestMapping("/update")
    @ResponseBody
    public void updateContentCategory(TbContentCategory contentCategory) {
        contentCategoryService.updateContentCategory(contentCategory);
    }
}
