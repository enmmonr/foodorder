package com.food.foodorder.controller;

import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.common.ServerResponse;
import com.food.foodorder.converter.OrderFormToOrderDtoConvert;
import com.food.foodorder.dto.OrderDto;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.form.OrderForm;
import com.food.foodorder.service.BuyerService;
import com.food.foodorder.service.ProductOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;
    @Autowired
    private BuyerService buyerService;
    @PostMapping("create")
    public ServerResponse<Map<String ,String>> createOrder(@Valid OrderForm orderForm, BindingResult bindingResult){
        //判断表单校验有么有错误
        if(bindingResult.hasErrors()){
            log.error("表单检验错误,orderForm={}",orderForm);
            throw new SellException(ProductStatusEnum.ILLEGAL_ARGUMENT);
        }
        OrderDto orderDto= OrderFormToOrderDtoConvert.convert(orderForm);
        //判断是否有商品
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("购物车为空");
            throw new SellException(ProductStatusEnum.CART_ISEMPTY);
        }

        return productOrderService.createOrder(orderDto);
    }

    //根据订单号查询订单
    @GetMapping("detail")
    public ServerResponse<OrderDto> orderDetail(@RequestParam(value = "orderId") String orderId,@RequestParam(value = "openId") String openId){
        // 安全性改进，防止越权
        return buyerService.findOrderOne(orderId,openId);
    }

    //订单列表查询
@GetMapping("list")
@Cacheable(cacheNames = "product",key = "123")  //添加缓存  第一次访问会进入方法，下次访问不会进入方法，直接访问缓存中的数据
    public ServerResponse<Page> list(@RequestParam(value = "openId",required = false) String openId,
                                     @RequestParam(value = "pageNum",defaultValue = "0") Integer pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        //TODO
    //openId判断
    String open="ew3euwhd7sjw9diwkq";
    PageRequest pageRequest=new PageRequest(pageNum,pageSize);
   Page<OrderDto> page= productOrderService.orderList(open,pageRequest);

    return ServerResponse.createBySuccessData(page);

}
@PostMapping("cancel")
public ServerResponse cancel(String openId,String orderId){

    return buyerService.cancel(openId,orderId);
}


}
