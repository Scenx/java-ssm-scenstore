package com.scen.order.controller;

import com.scen.pojo.ScenResult;
import com.scen.common.utils.ExceptionUtil;
import com.scen.pojo.Order;
import com.scen.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单表现层
 *
 * @author Scen
 * @date 2018/4/17 14:23
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ScenResult createOrder(@RequestBody Order order) {
        try {
            return orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
        } catch (Exception e) {
            e.printStackTrace();
            return ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
