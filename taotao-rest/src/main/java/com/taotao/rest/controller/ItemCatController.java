package com.taotao.rest.controller;

import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品分类列表
 *
 * @author Scen
 * @date 2018/4/2 10:49
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 返回商品分类json1
     *
     * @param callBack
     * @return
     */
    /*@RequestMapping(value = "/itemCat/list",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getItemList(String callBack) {
        CatResult catResult = itemCatService.getItemCatList();
        String json = JsonUtils.objectToJson(catResult);
//        拼装返回值
        String result = callBack + "(" + json + ");";
        return result;
    }*/

    /**
     * 返回商品分类json2
     *
     * @param callBack
     * @return
     */
    @RequestMapping("/itemCat/list")
    @ResponseBody
    public Object getItemCatList(String callBack) {
        CatResult catResult = itemCatService.getItemCatList();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
        mappingJacksonValue.setJsonpFunction(callBack);
        return mappingJacksonValue;
    }
}
