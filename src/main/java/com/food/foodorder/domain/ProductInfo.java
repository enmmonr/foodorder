package com.food.foodorder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.utils.EnumUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;


@Entity
@DynamicUpdate //自动更新数据库时间
@Data //包含get set toString等方法
public class ProductInfo {
    @Id //不设置自增，随机字符串
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDescription;
    private String productIcon;
    //商品状态，0正常1下架
    private Integer productStatus= ProductStatusEnum.UP.getCode();
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
       return EnumUtils.getByCode(productStatus,ProductStatusEnum.class);
    }

}
