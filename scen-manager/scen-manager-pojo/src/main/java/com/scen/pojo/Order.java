package com.scen.pojo;

import java.util.List;

/**
 * 订单pojo
 *
 * @author Scen
 * @date 2018/4/17 14:20
 */
public class Order extends TbOrder {

    private List<TbOrderItem> orderItems;

    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
