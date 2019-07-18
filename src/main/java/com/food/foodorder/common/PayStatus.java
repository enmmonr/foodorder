package com.food.foodorder.common;

import lombok.Getter;
@Getter
public enum  PayStatus implements CodeEnum{
    /*支付状态*/

        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款");

        private  Integer code;
        private String dec;
        PayStatus(Integer code,String dec){
            this.code=code;
            this.dec=dec;
        }
}
