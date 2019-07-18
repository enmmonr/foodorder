package com.food.foodorder.aspect;

import com.food.foodorder.common.Const;
import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.exception.SellAuthorizeException;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 身份校验aop
 */
@Aspect
@Component
@Slf4j
public class SellAuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.food.foodorder.controller.Seller*.*(..))"+
            "&& !execution(public * com.food.foodorder.controller.SellerUserController.*(..))")//切入点
    public void verify(){}//校验

    @Before("verify()")
    public void doVerify(){
        //获取request
        ServletRequestAttributes servletRequestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=servletRequestAttributes.getRequest();

        //查询cookie
       Cookie cookie= CookieUtils.getCookie(request, Const.TOKEN);
       if(cookie==null){
           log.warn("[登录校验]，cookie中查不到token");
           throw new SellAuthorizeException();
       }

       //在redis中查询token
        String token=stringRedisTemplate.opsForValue().get(String.format(Const.TOKEN_PREFIX,cookie.getValue()));
       if(StringUtils.isEmpty(token)){
           log.warn("[登录校验]，redis中查不到token");
           throw new SellAuthorizeException();
       }

    }
}
