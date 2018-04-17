package com.scen.order.service.impl;

import com.scen.pojo.ScenResult;
import com.scen.mapper.TbOrderItemMapper;
import com.scen.mapper.TbOrderMapper;
import com.scen.mapper.TbOrderShippingMapper;
import com.scen.order.dao.JedisClient;
import com.scen.order.service.OrderService;
import com.scen.pojo.TbOrder;
import com.scen.pojo.TbOrderItem;
import com.scen.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单处理业务层实现类
 *
 * @author Scen
 * @date 2018/4/17 13:19
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;

    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;

    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;

    @Override
    public ScenResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) {
//        获得订单号
        String result = jedisClient.get(ORDER_GEN_KEY);
        if (StringUtils.isBlank(result)) {
            jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
        }
        Long orderId = jedisClient.incr(ORDER_GEN_KEY);
//        补全pojo属性
        Date date = new Date();
        order.setOrderId(orderId + "");
        order.setCreateTime(date);
        order.setUpdateTime(date);
//        状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
//        0未评价1已经评价
        order.setBuyerRate(0);
//        向订单表中插入数据
        orderMapper.insert(order);
//        插入订单明细
        for (TbOrderItem tbOrderItem : itemList) {
//            补全订单明细
//            取订单明细id
            Long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            tbOrderItem.setId(orderDetailId + "");
            tbOrderItem.setOrderId(orderId + "");
//            向订单明细中插入记录
            orderItemMapper.insert(tbOrderItem);
        }
//        插入物流表
//        补全物流表的属性
        orderShipping.setOrderId(orderId + "");
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShippingMapper.insert(orderShipping);
        return ScenResult.ok(orderId);
    }
}
