package com.food.foodorder.repository;

import com.food.foodorder.domain.ProductCategory;
import com.food.foodorder.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    //根据商品状态查询商品
    List<ProductInfo> findByProductStatus(Integer productStatus);

    //根据商品分类查找商品
    List<ProductInfo> findByCategoryTypeIn(Integer categoryType);

}
