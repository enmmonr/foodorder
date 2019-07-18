package com.food.foodorder.service;

import com.food.foodorder.dto.OrderDto;

public interface PushMessageService {
    /**
     * 订单状态变更消息
     * @param orderDto
     */
    void orderStatus(OrderDto orderDto);
}
