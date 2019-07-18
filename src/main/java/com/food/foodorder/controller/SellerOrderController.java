package com.food.foodorder.controller;

import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.dto.OrderDto;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.service.ProductOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order/")
@Slf4j
public class SellerOrderController {
    @Autowired
    private ProductOrderService productOrderService;
    @RequestMapping("list")
    public ModelAndView list(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                             Map<String,Object> map){
        PageRequest pageRequest=new PageRequest(pageNum-1,pageSize);
       Page<OrderDto> page= productOrderService.findList(pageRequest);

       map.put("page",page);
       map.put("num",pageNum);
       map.put("size",pageSize);
       return new ModelAndView("order/list",map);
    }

    //订单取消
    @GetMapping("cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,Map<String,Object> map){
        try {
            OrderDto orderDto = productOrderService.findOneOrder(orderId);
            productOrderService.cancel(orderDto);
        }catch (SellException e){
            log.error("[卖家取消订单]，查不到此订单，orderId={}",orderId);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ProductStatusEnum.ORDER_CANCEL_SUCCESS.getDec());
        map.put("url","/sell/seller/order/list");

        return new ModelAndView("common/success",map);
    }

    //订单详情
    @GetMapping("detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,Map<String,Object> map){
        OrderDto orderDto=new OrderDto();
        try {
            orderDto = productOrderService.findOneOrder(orderId);
        }catch (SellException e){
            log.error("[卖家查看订单详情]发生异常，orderId={}",orderId);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("orderDto",orderDto);
        map.put("url","sell/seller/order/detail");

        return new ModelAndView("order/detail",map);
    }

    //订单完结
    @GetMapping("finish")
    public ModelAndView finish(@RequestParam("orderId")String orderId,Map<String,Object> map){
        try {
            OrderDto orderDto = productOrderService.findOneOrder(orderId);
            productOrderService.finish(orderDto);
        }catch (SellException e){
            log.error("[卖家完成订单]发生异常，orderId={}",orderId);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ProductStatusEnum.ORDER_FINISH_SUCCESS.getDec());
        map.put("url","sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }
}
