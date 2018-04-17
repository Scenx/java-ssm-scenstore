package com.scen.portal.service.impl;

import com.scen.common.utils.HttpClientUtil;
import com.scen.common.utils.JsonUtils;
import com.scen.pojo.Order;
import com.scen.pojo.ScenResult;
import com.scen.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 订单处理业务层实现类
 *
 * @author Scen
 * @date 2018/4/17 15:58
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;

    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;

    @Override
    public String createOrder(Order order) {
//        调用scen-order服务提交订单
        String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
//        把json转换成scenresult
        ScenResult scenResult = ScenResult.format(json);
        if (scenResult.getStatus() == 200) {
            Object orderId = scenResult.getData();
            return orderId.toString();
        }
        return "";
    }
}
