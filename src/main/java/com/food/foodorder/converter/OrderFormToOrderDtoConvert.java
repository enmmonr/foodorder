package com.food.foodorder.converter;

import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.domain.OrderDetail;
import com.food.foodorder.dto.OrderDto;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class OrderFormToOrderDtoConvert {
    public static OrderDto convert(OrderForm orderForm){
        OrderDto orderDto=new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenId());

        List<OrderDetail> list=null;
        //利用Json格式化返回list
        Gson gson=new Gson();
        try {
            list = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        }catch (Exception e){
        log.error("对象转换出错，String={}",orderForm.getItems());
        throw new SellException(ProductStatusEnum.ILLEGAL_ARGUMENT);
        }
        orderDto.setOrderDetailList(list);
        return orderDto;
    }
}
