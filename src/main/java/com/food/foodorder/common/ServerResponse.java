package com.food.foodorder.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

/*结果响应类*/
@JsonSerialize
@Getter
public class ServerResponse<T> {

// 信息代码
    private int code;
//    返回信息
    private String msg;
//    返回数据类型
    private T data;


//    判断是否成功
    @JsonIgnore  //使之不在序列化结果当中
    public boolean isSuccess(){
        return this.code==ResponseCode.SUCCESS.getCode();
    }

    private ServerResponse(int code){
        this.code=code;
    }

    private ServerResponse(int code,T data){
        this.code=code;
        this.data=data;
    }

    private ServerResponse(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
    private ServerResponse(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
    public static <T> ServerResponse<T> createBySuccess(){
        return  new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createBySuccessData(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccessMsgAndData(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    public static <T> ServerResponse<T> createByError(){
        return  new ServerResponse<T>(ResponseCode.ERROR.getCode());
    }

    public static <T> ServerResponse<T> createByErrorMsg(String errorMsg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMsg);
    }
    public static <T> ServerResponse<T> createByErrorCodeMsg(int errorCode,String errorMsg){
        return new ServerResponse<>(errorCode,errorMsg);
    }



}
