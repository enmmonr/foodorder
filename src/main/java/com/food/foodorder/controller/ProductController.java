package com.food.foodorder.controller;


import com.food.foodorder.common.ServerResponse;
import com.food.foodorder.service.ProductService;
import com.food.foodorder.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("list")
    public ServerResponse<List<ProductVo>> list(){
       return ServerResponse.createBySuccessData(productService.findAll());
    }
}
