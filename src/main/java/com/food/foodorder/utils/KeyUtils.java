package com.food.foodorder.utils;

import java.util.Random;

public class KeyUtils {
    //生成唯一id
    public static synchronized String  generateOrderId(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;

        return  System.currentTimeMillis()+String.valueOf(number);
    }
}
