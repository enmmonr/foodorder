package com.food.foodorder.repository;

import com.food.foodorder.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void add(){
        ProductCategory productCategory=new ProductCategory("最热",3);
        ProductCategory result= productCategoryRepository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryType(){
        List<Integer> categoryList= Arrays.asList(1,2,3);
        List<ProductCategory> result=productCategoryRepository.findByCategoryTypeIn(categoryList);
        Assert.assertNotEquals(0,result.size());
    }


}