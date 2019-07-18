package com.food.foodorder.handler;

import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.common.ServerResponse;
import com.food.foodorder.config.ProjectUrlConfig;
import com.food.foodorder.exception.SellAuthorizeException;
import com.food.foodorder.exception.SellException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //异常拦截处理注解，返回json格式可用@RestController
public class SellerLoginHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(SellAuthorizeException.class)//拦截登录异常
    public ModelAndView handlerAuthorizeException(){

        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWeChatOpenAuthorizeUrl())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getProjectUrl())
                .concat("/sell/seller/login"));

    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ServerResponse handlerException(SellException e){
        return  ServerResponse.createByErrorCodeMsg(e.getCode(),e.getMessage());
    }

}
