package com.food.foodorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "projecturl")
public class ProjectUrlConfig {
    /**
     * 微信公众平台授权url
     */
    private String weChatMpAuthorizeUrl;

    /**
     * 微信开放平台授权url
     */
    private String weChatOpenAuthorizeUrl;

    /**
     * 点餐系统url
     */
    private String projectUrl;
}
