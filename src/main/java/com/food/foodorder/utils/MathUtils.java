package com.food.foodorder.utils;

public class MathUtils {

    private static final Double MONEY_RANG=0.01;
    /*比较两个金额是否相等*/
    public static Boolean equals(Double a,Double b){
        Double result=Math.abs(a-b);
        if(result<MONEY_RANG){
            return true;
        }else {
            return false;
        }

    }
}
