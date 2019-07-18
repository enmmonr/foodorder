package com.food.foodorder.dto;

import lombok.Data;

@Data
public class CartDto {
    private String productId;
    private Integer productQuality;
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getProductQuality() {
        return productQuality;
    }

    public void setProductQuality(Integer productQuality) {
        this.productQuality = productQuality;
    }
    public CartDto(String productId, Integer productQuality){
        this.productId=productId;
        this.productQuality=productQuality;
    }

    public CartDto(){}

}
