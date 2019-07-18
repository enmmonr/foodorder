package com.food.foodorder.service.impl;

import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.common.ServerResponse;
import com.food.foodorder.dto.OrderDto;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.service.BuyerService;
import com.food.foodorder.service.ProductOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private ProductOrderService productOrderService;
    @Override
    public ServerResponse<OrderDto> findOrderOne(String orderId, String openId) {
        OrderDto orderDto=this.checkOrderIdAndOpenId(orderId,openId);

        return ServerResponse.createBySuccessMsgAndData("成功",orderDto);
    }

    @Override
    public ServerResponse cancel(String orderId, String openId) {
        OrderDto orderDto=this.checkOrderIdAndOpenId(orderId,openId);
       if(orderDto==null){
           log.error("没有此订单，orderId={}，openId={}",orderId,openId);
           throw new SellException(ProductStatusEnum.PRODUCT_NOT_EXIST);
       }
        return productOrderService.cancel(orderDto);
    }

    private OrderDto checkOrderIdAndOpenId(String orderId, String openId){
        OrderDto orderDto=productOrderService.findOneOrder(orderId);

        if(orderDto==null){
            return null;
        }
        //判断是否为本人订单
        //equalsIgnoreCase()忽略大小写的比较方法
        if(!orderDto.getBuyerOpenid().equalsIgnoreCase(openId)){
            log.error("【订单查找】不是本人订单，openId不一致，orderId={}，openId={}",orderId,openId);
            throw new SellException(ProductStatusEnum.ILLEGAL_ARGUMENT);
        }
        return orderDto;
    }
}
