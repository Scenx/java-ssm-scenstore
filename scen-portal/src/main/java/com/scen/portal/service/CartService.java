package com.scen.portal.service;

import com.scen.pojo.CartItem;
import com.scen.pojo.ScenResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车业务层接口
 *
 * @author Scen
 * @date 2018/4/12 13:35
 */
public interface CartService {
    /**
     * 添加购物车
     *
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    ScenResult addCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);


    /**
     * 展示购物车
     *
     * @param request
     * @param response
     * @return
     */
    List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);


    /**
     * 根据商品id删除购物车商品
     *
     * @param itemId
     * @return
     */
    ScenResult deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response);

}
