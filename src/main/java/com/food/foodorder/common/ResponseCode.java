package com.food.foodorder.common;

import lombok.Getter;

@Getter
public enum ResponseCode {

/*前台响应状态*/

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    ILLEGAL_ARGUMENT(20,"ILLEGAL_ARGUMENT");
private int code;
private String msg;

ResponseCode(int code,String msg){
    this.code=code;
    this.msg=msg;
}
}
