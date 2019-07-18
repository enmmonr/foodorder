package com.food.foodorder.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/*订单表单处理类*/
@Data
public class OrderForm {
    @NotEmpty(message = "姓名不能为空")
    private String name;
    @NotEmpty(message = "电话不能为空")
    private String phone;
    @NotEmpty(message = "地址不能为空")
    private String address;
    @NotEmpty(message = "openID不能为空")
    private String openId;
    @NotEmpty(message = "items不能为空")
    private String items;

}
