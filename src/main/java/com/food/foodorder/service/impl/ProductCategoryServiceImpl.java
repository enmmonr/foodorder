package com.food.foodorder.service.impl;

import com.food.foodorder.domain.ProductCategory;
import com.food.foodorder.repository.ProductCategoryRepository;
import com.food.foodorder.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
   private ProductCategoryRepository productCategoryRepository;
    @Override
    public ProductCategory findOneById(Integer categoryId) {
        return productCategoryRepository.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        productCategoryRepository.deleteById(id);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryList);
    }

    @Override
    public void save(ProductCategory productCategory) {

        productCategoryRepository.save(productCategory);
    }
}
