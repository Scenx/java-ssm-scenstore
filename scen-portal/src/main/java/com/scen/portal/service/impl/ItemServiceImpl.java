package com.scen.portal.service.impl;

import com.scen.common.pojo.ScenResult;
import com.scen.common.utils.HttpClientUtil;
import com.scen.common.utils.JsonUtils;
import com.scen.pojo.TbItemDesc;
import com.scen.pojo.TbItemParamItem;
import com.scen.portal.pojo.ItemInfo;
import com.scen.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 展示商品业务层实现类
 *
 * @author Scen
 * @date 2018/4/10 10:52
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${ITEM_PARAM_URL}")
    private String ITEM_PARAM_URL;


    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;


    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Override
    public ItemInfo getItemById(Long itemId) {
        try {
//        调用rest的服务查询商品基础信息
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
            if (!StringUtils.isBlank(json)) {
                ScenResult scenResult = ScenResult.formatToPojo(json, ItemInfo.class);
                if (scenResult.getStatus() == 200) {
                    ItemInfo item = (ItemInfo) scenResult.getData();
                    return item;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemDescById(Long itemId) {
        try {
//            查询商品描述
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
//            转换成json对象
            ScenResult scenResult = ScenResult.formatToPojo(json, TbItemDesc.class);
            if (scenResult.getStatus() == 200) {
                TbItemDesc itemDesc = (TbItemDesc) scenResult.getData();
//                取商品描述信息
                String result = itemDesc.getItemDesc();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getItemParamItem(Long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
//            把json转换成java对象
            ScenResult scenResult = ScenResult.formatToPojo(json, TbItemParamItem.class);
            if (scenResult.getStatus() == 200) {
                TbItemParamItem itemParamItem = (TbItemParamItem) scenResult.getData();
                String paramData = itemParamItem.getParamData();
                //生成html
                // 把规格参数json数据转换成java对象
                List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
                StringBuffer sb = new StringBuffer();
                sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                sb.append("    <tbody>\n");
                for (Map m1 : jsonList) {
                    sb.append("        <tr>\n");
                    sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
                    sb.append("        </tr>\n");
                    List<Map> list2 = (List<Map>) m1.get("params");
                    for (Map m2 : list2) {
                        sb.append("        <tr>\n");
                        sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
                        sb.append("            <td>" + m2.get("v") + "</td>\n");
                        sb.append("        </tr>\n");
                    }
                }
                sb.append("    </tbody>\n");
                sb.append("</table>");
                //返回html片段
                return sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
