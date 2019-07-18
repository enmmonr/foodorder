package com.food.foodorder.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//Json格式化工具
public class JsonUtils {

    public static String toJson(Object o){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson=gsonBuilder.create();
       return gson.toJson(o);
    }

}
