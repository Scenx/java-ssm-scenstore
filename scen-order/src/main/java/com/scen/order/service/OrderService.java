package com.scen.order.service;

import com.scen.pojo.ScenResult;
import com.scen.pojo.TbOrder;
import com.scen.pojo.TbOrderItem;
import com.scen.pojo.TbOrderShipping;

import java.util.List;

/**
 * 订单处理业务层接口
 *
 * @author Scen
 * @date 2018/4/17 13:17
 */
public interface OrderService {

    /**
     * 创建一个订单
     *
     * @param order
     * @param itemList
     * @param orderShipping
     * @return
     */
    ScenResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping);
}
