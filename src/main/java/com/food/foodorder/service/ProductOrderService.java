package com.food.foodorder.service;

import com.food.foodorder.common.ServerResponse;
import com.food.foodorder.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductOrderService {

    //查找一个订单
    OrderDto findOneOrder(String orderId);
    //订单列表
    Page<OrderDto> orderList(String openId, Pageable pageable);

    //创建一个订单
    ServerResponse<Map<String,String>> createOrder(OrderDto orderDto);

    //完成订单
    ServerResponse<OrderDto> finish(OrderDto orderDto);

    //支付订单
    ServerResponse<OrderDto> paid(OrderDto orderDto);
    //取消订单
    ServerResponse cancel(OrderDto orderDto);

    Page<OrderDto> findList(Pageable pageable);
}
