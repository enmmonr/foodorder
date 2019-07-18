package com.food.foodorder.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.food.foodorder.common.OrderStatus;
import com.food.foodorder.common.PayStatus;
import com.food.foodorder.domain.OrderDetail;
import com.food.foodorder.utils.DateJsonSerializer;
import com.food.foodorder.utils.EnumUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    //订单总价
    private BigDecimal orderAmount;
    //支付状态
    private Integer payStatus= PayStatus.NO_PAY.getCode();
    //订单状态 默认为新下单
    private Integer orderStatus= OrderStatus.NEW_ORDER.getCode();
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date createTime;
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date updateTime;
    private List<OrderDetail> orderDetailList;


    @JsonIgnore //忽略此方法
    public OrderStatus getOrderStatusEnum(){
        return  EnumUtils.getByCode(orderStatus,OrderStatus.class);
    }

    @JsonIgnore //忽略此方法
    public PayStatus getPayStatusEnum(){
        return  EnumUtils.getByCode(payStatus,PayStatus.class);
    }
}
