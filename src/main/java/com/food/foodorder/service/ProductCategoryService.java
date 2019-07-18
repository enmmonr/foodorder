package com.food.foodorder.service;

import com.food.foodorder.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    //查找一个
    ProductCategory findOneById(Integer categoryId);

    //查询所有分类
    List<ProductCategory> findAll();

    //删除分类
    void deleteById(Integer id);


    //根据类目编号查询所有类目
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);

    //保存或更新分类
    void save(ProductCategory productCategory);

}
