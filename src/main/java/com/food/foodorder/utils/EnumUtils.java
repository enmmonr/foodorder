package com.food.foodorder.utils;

import com.food.foodorder.common.CodeEnum;

/**
 * 获取状态的枚举工具类，
 * 根据传入的Class以及枚举代码返回枚举
 */
public class EnumUtils{

    public static <T extends CodeEnum>T getByCode(Integer code, Class<T> enumClass){
        for (T each:enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return  each;
            }
        }
        return null;
    }
}
