package com.food.foodorder.service;

import com.food.foodorder.domain.SellerInfo;

public interface SellerInfoUserService {
    SellerInfo save(SellerInfo sellerInfo);

    //通过openId查找一个用户
    SellerInfo findByOpenId(String openId);

}
