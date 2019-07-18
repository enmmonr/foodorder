package com.food.foodorder.controller;

import com.food.foodorder.domain.ProductCategory;
import com.food.foodorder.domain.ProductInfo;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.form.ProductForm;
import com.food.foodorder.service.ProductCategoryService;
import com.food.foodorder.service.ProductService;
import com.food.foodorder.utils.KeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @RequestMapping("list")
    public ModelAndView list(@RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                             @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                             Map<String,Object> map){

        PageRequest pageRequest=new PageRequest(pageNum-1,pageSize);
        Page<ProductInfo> productInfos=productService.findList(pageRequest);

       map.put("productInfos",productInfos);
       map.put("pageNum",pageNum);
       map.put("pageSize",pageSize);
       return new ModelAndView("product/list",map);
    }

    @RequestMapping("on_sale")
    public ModelAndView onSell(@RequestParam("productId")String productId,Map<String,Object> map){
        try {
            productService.upProduct(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @RequestMapping("off_sale")
    public ModelAndView offSell(@RequestParam("productId")String productId,Map<String,Object> map){
        try {
            productService.downProduct(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "productId",required = false)String productId,
                              Map<String,Object> map){
        //判断productId 是否为空
        if(StringUtils.isNotEmpty(productId)){
             ProductInfo productInfo=productService.findOne(productId);
             map.put("productInfo",productInfo);
        }

        //查询所有类目
        List<ProductCategory> productCategoryList=productCategoryService.findAll();
        map.put("productCategoryList",productCategoryList);

        return new ModelAndView("product/index",map);

    }

//    更新或修改商品信息
    @PostMapping("save")
    //@CachePut(cacheNames = "product",key = "456") //每次访问会进入方法，并将返回的对象放入redis中
    @CacheEvict(cacheNames = "product",key = "456") //清除缓存
    public ModelAndView save( @Valid ProductForm productForm, BindingResult bindingResult,
                             Map<String, Object> map){
        if (bindingResult.hasErrors()){
            //数据校验失败
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo=new ProductInfo();
        try{
        if (StringUtils.isNotEmpty(productForm.getProductId())){
            //不为空，更新，取数据
            productInfo=productService.findOne(productForm.getProductId());

        }else{
            String productId= KeyUtils.generateOrderId();
            productForm.setProductId(productId);
        }
            BeanUtils.copyProperties(productForm,productInfo);
            productService.save(productInfo);
    } catch (SellException e) {
        map.put("msg", e.getMessage());
        map.put("url", "/sell/seller/product/index");
        return new ModelAndView("common/error", map);
    }


        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

 }
