package com.food.foodorder.service.impl;

import com.food.foodorder.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductCategoryServiceImplTest {
    @Autowired
   private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void findOneById() {
       ProductCategory productCategory= productCategoryService.findOneById(2);
     System.out.println(1);

    }
    @Test
    public void findAll() {
     List list=productCategoryService.findAll();
    }

    @Test
    public void deleteById() {
     productCategoryService.deleteById(5);
    }

    @Test
    public void findByCategoryTypeIn() {
    }

    @Test
    public void save() {
     ProductCategory productCategory=new ProductCategory("最热",4);
     productCategoryService.save(productCategory);
    }
}