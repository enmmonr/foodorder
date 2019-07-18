package com.food.foodorder.service;

import com.food.foodorder.common.ServerResponse;
import com.food.foodorder.dto.OrderDto;

public interface BuyerService {

    ServerResponse<OrderDto> findOrderOne(String orderId,String openId);

    ServerResponse cancel(String orderId,String openId);

}
