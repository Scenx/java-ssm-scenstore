package com.scen.portal.service.impl;

import com.scen.common.pojo.CartItem;
import com.scen.common.pojo.ScenResult;
import com.scen.common.utils.CookieUtils;
import com.scen.common.utils.HttpClientUtil;
import com.scen.common.utils.JsonUtils;
import com.scen.pojo.TbItem;
import com.scen.portal.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车业务层实现类
 *
 * @author Scen
 * @date 2018/4/12 13:37
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Value("${SCEN_CART_NAME}")
    private String SCEN_CART_NAME;


    @Override
    public ScenResult addCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        CartItem cartItem = null;
//取购物车商品列表
        List<CartItem> itemList = getCartItemList(request);
//判断商品列表中是否存在此商品
        for (CartItem cItem : itemList) {
//            如果存在此商品
            if (cItem.getId().equals(itemId)) {
//                增加商品数量
                cItem.setNum(cItem.getNum() + num);
                cartItem = cItem;
                break;
            }
        }
        if (cartItem == null) {
            cartItem = new CartItem();
//        取商品信息
//        根据商品id查询商品基本信息
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
//        把json转为java对象
            ScenResult scenResult = ScenResult.formatToPojo(json, TbItem.class);
            if (scenResult.getStatus() == 200) {
                TbItem item = (TbItem) scenResult.getData();
                cartItem.setId(item.getId());
                cartItem.setTitle(item.getTitle());
                cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                cartItem.setNum(num);
                cartItem.setPrice(item.getPrice());
            }
//            添加到购物车列表
            itemList.add(cartItem);
        }
//        把购物车列表写入cookie
        CookieUtils.setCookie(request, response, SCEN_CART_NAME, JsonUtils.objectToJson(itemList), true);
        return ScenResult.ok();
    }

    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        return getCartItemList(request);
    }

    /**
     * 从cookie中取商品列表
     *
     * @return
     */
    private List<CartItem> getCartItemList(HttpServletRequest request) {
//        从cookie中取商品列表
        String cartJson = CookieUtils.getCookieValue(request, SCEN_CART_NAME, true);
        if (StringUtils.isBlank(cartJson)) {
            return new ArrayList<>();
        }

        try {
//        把json转换成商品列表
            List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
