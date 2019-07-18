package com.food.foodorder.converter;

import com.food.foodorder.domain.OrderMaster;
import com.food.foodorder.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMasterToOrderDtoConverter {

    public static OrderDto convert(OrderMaster orderMaster){
        OrderDto orderDto=new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }

    public static List<OrderDto> convert(List<OrderMaster> orderMasters){
        List<OrderDto> orderDtoList=orderMasters.stream().map(e-> convert(e)).collect(Collectors.toList());

        return orderDtoList;


    }
}
