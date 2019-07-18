package com.food.foodorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching //使用缓存
public class FoodorderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodorderApplication.class, args);
    }

}
