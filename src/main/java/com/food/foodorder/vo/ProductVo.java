package com.food.foodorder.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 包含分类
 */
@Data
public class ProductVo implements Serializable {
    private static final long serialVersionUID = 1L;
@JsonProperty("name")
private String categoryName;
@JsonProperty("type")
private Integer categoryType;
@JsonProperty("foods")
private List<ProductInfoVo> productInfoVo;

}
