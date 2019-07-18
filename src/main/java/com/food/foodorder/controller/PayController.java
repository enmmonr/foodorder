package com.food.foodorder.controller;

import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.dto.OrderDto;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.service.PayService;
import com.food.foodorder.service.ProductOrderService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    //支付订单创建
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl")String returnUrl,
                               Map<String,Object> map){
        //查找订单
        OrderDto orderDto=productOrderService.findOneOrder(orderId);
        if(orderDto==null){
            log.error("【微信支付】订单查找;不存在此订单。orderId={}",orderId);
            throw new SellException(ProductStatusEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        PayResponse payResponse=payService.create(orderDto);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create",map);

    }
    //微信支付异步通知
    @PostMapping("notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);
        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }

}
