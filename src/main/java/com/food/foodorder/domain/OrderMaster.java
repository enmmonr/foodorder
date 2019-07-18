package com.food.foodorder.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.food.foodorder.common.OrderStatus;
import com.food.foodorder.common.PayStatus;
import com.food.foodorder.utils.DateJsonSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
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
    @JsonSerialize(using = DateJsonSerializer.class)//除掉毫秒
    private Date createTime;
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date updateTime;
}
