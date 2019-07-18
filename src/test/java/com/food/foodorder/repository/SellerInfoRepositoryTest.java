package com.food.foodorder.repository;

import com.food.foodorder.domain.SellerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void add(){
        SellerInfo sellerInfo=new SellerInfo()
                .setSellerId("12345")
                .setUsername("admin")
                .setPassword("admin")
                .setOpenId("461316assd231");
        sellerInfoRepository.save(sellerInfo);
    }

    @Test
    public void findByOpenId(){

       SellerInfo sellerInfo= sellerInfoRepository.findByOpenId("461316assd231");
        log.info("sellInfo={}",sellerInfo);
    }
}