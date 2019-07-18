package com.food.foodorder.common;
/**
 * Redis常量
 * */
public interface Const {
     String TOKEN_PREFIX="token_";
     //token过期时间
    Integer EXPIRE=7200; //2h

    String TOKEN="token";
}
