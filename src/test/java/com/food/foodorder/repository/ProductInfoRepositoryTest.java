package com.food.foodorder.repository;

import com.food.foodorder.domain.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;



@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void add(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("宫保鸡丁");
        productInfo.setProductPrice(new BigDecimal(66));
        productInfo.setProductDescription("naisi");
        productInfo.setProductIcon("https://123.jpg");
        productInfo.setProductStock(20);
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        productInfoRepository.save(productInfo);
    }

    @Test
    public void findByProductStatus(){
        List<ProductInfo> productInfoList=productInfoRepository.findByProductStatus(0);
    }

}