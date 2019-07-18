package com.food.foodorder.repository;

import com.food.foodorder.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    //根据类目编号查询查询所有类目
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    //根据类目编号查询类目
    ProductCategory findByCategoryTypeIn(Integer categoryType);
}
