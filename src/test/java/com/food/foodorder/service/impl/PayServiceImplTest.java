package com.food.foodorder.service.impl;

import com.food.foodorder.dto.OrderDto;
import org.hibernate.criterion.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PayServiceImplTest {
    @Autowired
    private ProductOrderServiceImpl productOrderService;
    @Autowired
    private PayServiceImpl payService;

    @Test
    public void create() {
        OrderDto orderDto=productOrderService.findOneOrder("1561955268166808659");
        payService.create(orderDto);
    }
}