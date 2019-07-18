package com.food.foodorder.service.impl;

import com.food.foodorder.domain.SellerInfo;
import com.food.foodorder.repository.SellerInfoRepository;
import com.food.foodorder.service.SellerInfoUserService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerInfoUserServiceImpl implements SellerInfoUserService {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;
    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return null;
    }

    @Override
    public SellerInfo findByOpenId(String openId) {
        return sellerInfoRepository.findByOpenId(openId);
    }
}
