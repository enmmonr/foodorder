package com.food.foodorder.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@Accessors(chain = true) //链式代码
public class SellerInfo {

    @Id
    private String sellerId;
    private String username;
    private String password;
    private String openId;
    private Date createTime;
    private Date updateTime;
}
