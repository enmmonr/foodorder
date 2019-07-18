package com.food.foodorder.service.impl;

import com.food.foodorder.common.ServerResponse;
import com.food.foodorder.domain.ProductCategory;
import com.food.foodorder.domain.ProductInfo;
import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.dto.CartDto;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.repository.ProductCategoryRepository;
import com.food.foodorder.repository.ProductInfoRepository;
import com.food.foodorder.service.ProductService;
import com.food.foodorder.vo.ProductInfoVo;
import com.food.foodorder.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;



    @Override
    public ProductInfo findOne(String id) {
        return productInfoRepository.findById(id).get();
    }

    @Override
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);

    }

    public Page<ProductInfo> findList(Pageable pageable){
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public List<ProductVo> findAll() {
        //从数据库获取商品信息
       List<ProductInfo> productInfoList=productInfoRepository.findAll();

       //查找商品分类信息(一次性查询)   lambda表达式
        List<Integer> categoryTypeList=productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categoryList=productCategoryRepository.findByCategoryTypeIn(categoryTypeList);

        //数据组装
        List<ProductVo> productVoList=new ArrayList<>();
        for (ProductCategory p:categoryList) {
            ProductVo productVo=new ProductVo();
            productVo.setCategoryName(p.getCategoryName());
            productVo.setCategoryType(p.getCategoryType());

            List<ProductInfoVo> productInfoVoList=new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if(p.getCategoryType().equals(productInfo.getCategoryType())) {
                    ProductInfoVo productInfoVo=new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);

                }
            }
            productVo.setProductInfoVo(productInfoVoList);
            productVoList.add(productVo);

        }
        return productVoList;
    }

    @Override
    @Transactional
    public void increaseQuantity(List<CartDto> cartDtoList) {
        for(CartDto cartDto:cartDtoList){
          ProductInfo productInfo=  productInfoRepository.findById(cartDto.getProductId()).get();
            if(productInfo==null){
                throw new SellException(ProductStatusEnum.PRODUCT_NOT_EXIST);
            }
            Integer productStock=productInfo.getProductStock();
            Integer result=productStock+cartDto.getProductQuality();
            if(result<=productStock){
                throw new SellException(ProductStatusEnum.PRODUCT_QUANTITY_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }


    }

    @Override
    @Transactional
    public void reduceQuantity(List<CartDto> cartDtoList) {
        for (CartDto cartDto:cartDtoList
             ) {
           ProductInfo productInfo= productInfoRepository.findById(cartDto.getProductId()).get();
           if(productInfo==null){
               throw new SellException(ProductStatusEnum.PRODUCT_NOT_EXIST);
           }
           Integer result=productInfo.getProductStock()-cartDto.getProductQuality();
           if(result<0){
               throw new SellException(ProductStatusEnum.PRODUCT_QUANTITY_ERROR);
           }
           productInfo.setProductStock(result);
           productInfoRepository.save(productInfo);
        }
    }

    public void upProduct(String productId){
        //查找商品
        ProductInfo productInfo=productInfoRepository.getOne(productId);
        if(productInfo==null){
            log.error("[商品上架]查不到此商品,productId={}",productId);
            throw new SellException(ProductStatusEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus()==ProductStatusEnum.UP.getCode()){
            throw  new SellException(ProductStatusEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
       ProductInfo result= productInfoRepository.save(productInfo);


    }

    public void downProduct(String productId){
        //查找商品
        ProductInfo productInfo=productInfoRepository.getOne(productId);
        if(productInfo==null){
            log.error("[商品下架]查不到此商品,productId={}",productId);
            throw new SellException(ProductStatusEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus()==ProductStatusEnum.DOWN.getCode()){
            throw  new SellException(ProductStatusEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        ProductInfo result= productInfoRepository.save(productInfo);
    }
}
