package com.food.foodorder.service;

import com.food.foodorder.common.ServerResponse;
import com.food.foodorder.domain.ProductInfo;
import com.food.foodorder.dto.CartDto;
import com.food.foodorder.vo.ProductVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    //根据id查询
    ProductInfo findOne(String id);

    //根据状态查找商品
    List<ProductInfo> findByProductStatus(Integer productStatus);

    //新增商品
    ProductInfo save(ProductInfo productInfo);

    //查找所有商品
    List<ProductVo> findAll();
    Page<ProductInfo> findList(Pageable pageable);
    //加库存
    void  increaseQuantity(List<CartDto> cartDtoList);
    //减库存
    void  reduceQuantity(List<CartDto> cartDtoList);
    //产品上架
    void upProduct(String productId);
    //产品上架
    void downProduct(String productId);

}
