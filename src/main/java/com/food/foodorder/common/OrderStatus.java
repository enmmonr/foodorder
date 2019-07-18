package com.food.foodorder.common;

import lombok.Getter;

@Getter
public enum  OrderStatus implements CodeEnum{

        /*订单状态*/

        NEW_ORDER(0,"新订单"),
        FINISH_ORDER(1,"已完成"),
        CANCEL_ORDER(10,"已取消");

        private  Integer code;
        private String dec;
        OrderStatus(Integer code,String dec){
            this.code=code;
            this.dec=dec;
        }

}
