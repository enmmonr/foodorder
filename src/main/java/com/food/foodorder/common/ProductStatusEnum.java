package com.food.foodorder.common;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum{

    /*产品状态*/
    UP(0,"在架"),
    DOWN(1,"下架"),
    PRODUCT_NOT_EXIST(2,"商品不存在"),
    PRODUCT_QUANTITY_ERROR(3,"商品库存不正确"),
    PRODUCT_STATUS_ERROR(4,"商品状态错误"),
    ORDER_NOT_EXIST(20,"订单不存在"),
    ORDER_CANCEL_SUCCESS(24,"订单取消成功"),
    ORDER_FINISH_SUCCESS(25,"订单完结成功"),
    ORDER_DETAIL_NOT_EXIST(21,"订单详情不存在"),
    ORDER_STATUS_ERROR(22,"订单状态错误"),
    ORDER_STATUS_UPDATE_ERROR(23,"订单状态更新失败"),
    PAID_STATUS_ERROR(30,"支付状态错误"),
    PAID_STATUS_UPDATE_ERROR(31,"支付状态更新错误"),
    ILLEGAL_ARGUMENT(40,"参数错误"),
    CART_ISEMPTY(50,"购物车为空"),
    ORDER_OWNER_ERROR(60, "该订单不属于当前用户"),

    WECHAT_MP_ERROR(61, "微信公众账号方面错误"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(62, "微信支付异步通知金额校验不通过"),
    LOGIN_FAIL(70, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(71, "登出成功"),;

    private Integer code;
    private String dec;

    ProductStatusEnum(Integer code, String dec){
        this.code=code;
        this.dec=dec;
    }

}
