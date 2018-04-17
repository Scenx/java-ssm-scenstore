package com.scen.portal.service;

import com.scen.pojo.Order;

/**
 * 订单处理业务层接口
 *
 * @author Scen
 * @date 2018/4/17 15:55
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    String createOrder(Order order);
}
