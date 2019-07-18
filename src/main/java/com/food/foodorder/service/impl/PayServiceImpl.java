package com.food.foodorder.service.impl;

import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.dto.OrderDto;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.service.PayService;
import com.food.foodorder.service.ProductOrderService;
import com.food.foodorder.utils.JsonUtils;
import com.food.foodorder.utils.MathUtils;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//支付
@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME="微信支付订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private ProductOrderService productOrderService;
    @Override
    public PayResponse create(OrderDto orderDto) {
        PayRequest payRequest=new PayRequest();
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        payRequest.setOrderName(ORDER_NAME);
        log.info("【微信支付】发起支付，request={}", JsonUtils.toJson(payRequest));

        PayResponse payResponse=bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付，response={}", JsonUtils.toJson(payResponse));
        return payResponse;

    }

    public PayResponse notify(String notifyData){
        //1. 验证签名
        //2. 支付的状态
        //3. 支付金额
        //4. 支付人(下单人 == 支付人)

        PayResponse payResponse=bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，response={}", JsonUtils.toJson(payResponse));

        OrderDto orderDto=productOrderService.findOneOrder(payResponse.getOrderId());
        if(orderDto==null){
            log.error("【微信支付】异步通知;不存在此订单。orderId={}",payResponse.getOrderId());
            throw new SellException(ProductStatusEnum.ORDER_NOT_EXIST);
        }
//判断金额是否相等
        if(!MathUtils.equals(payResponse.getOrderAmount(),orderDto.getOrderAmount().doubleValue())){
            log.error("【微信支付】异步通知;订单金额不一致，orderId={},微信金额={},订单金额={}",
                    payResponse.getOrderId(),payResponse.getOrderAmount(),orderDto.getOrderAmount());
            throw new SellException(ProductStatusEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        productOrderService.paid(orderDto);
        return payResponse;
    }

    /*
    * 微信退款*/
    @Override
    public RefundResponse refund(OrderDto orderDto) {
        RefundRequest refundRequest=new RefundRequest();
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】refundRequest={}",JsonUtils.toJson(refundRequest));
        RefundResponse refundResponse= bestPayService.refund(refundRequest);

        log.info("【微信退款】refundResponse={}",JsonUtils.toJson(refundResponse));
        return refundResponse;
    }
}
