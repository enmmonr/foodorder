package com.food.foodorder.controller;

import com.food.foodorder.common.Const;
import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.config.ProjectUrlConfig;
import com.food.foodorder.domain.SellerInfo;
import com.food.foodorder.service.SellerInfoUserService;
import com.food.foodorder.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller/user")
public class SellerUserController {
    @Autowired
    private SellerInfoUserService sellerInfoUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("login")
    public ModelAndView login(@RequestParam("openId") String openId, Map<String ,Object> map, HttpServletResponse response){
        //查找数据库中是否有匹配的openID
      SellerInfo sellerInfo= sellerInfoUserService.findByOpenId(openId);
      if (sellerInfo==null){
          map.put("msg", ProductStatusEnum.LOGIN_FAIL.getDec());
          map.put("url","/sell/seller/order/list");
          return new ModelAndView("common/error",map);
      }

      //设置token到redis
         //生成token
        String token= UUID.randomUUID().toString();
        System.out.println(token);
        //设置过期时间
        Integer expire=Const.EXPIRE;
        //设置redis数据格式并添加
        stringRedisTemplate.opsForValue().set(String.format(Const.TOKEN_PREFIX,token),openId,expire, TimeUnit.SECONDS);
        System.out.println(String.format(Const.TOKEN_PREFIX,token));

        //设置token到cookie;
        CookieUtils.setCookie(response,Const.TOKEN,token,expire);
      return  new ModelAndView("redirect:/seller/order/list");
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpServletResponse response, HttpServletRequest request,
                               Map<String,Object> map){
//        1.获取cookie
        Cookie cookie=CookieUtils.getCookie(request,Const.TOKEN);
        if(cookie!=null){

            //2.将token从redis中删除
            System.out.println(String.format(Const.TOKEN_PREFIX,cookie.getValue()));
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(Const.TOKEN_PREFIX,cookie.getValue()));

            //3.清楚cookie
            CookieUtils.setCookie(response,Const.TOKEN,null,0);


        }
        map.put("msg",ProductStatusEnum.LOGOUT_SUCCESS.getDec());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }
}
