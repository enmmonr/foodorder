package com.food.foodorder.exception;

import com.food.foodorder.common.ProductStatusEnum;
import lombok.Getter;

//异常类
@Getter
public class SellException extends RuntimeException{
    private int code;

    public SellException(ProductStatusEnum productStatusEnum){
        super(productStatusEnum.getDec());
        this.code=productStatusEnum.getCode();
    }
}
