package com.food.foodorder.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
public class WeChatController {
/*    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    @GetMapping("authorize")
    //网页授权
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url=projectUrlConfig.getWeChatMpAuthorizeUrl();
       String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, returnUrl);
       return "redirect:"+redirectUrl;

    }

    @GetMapping("userInfo")
    //用户信息
    public String userInfo(@RequestParam("code") String code,@RequestParam("returnUrl") String returnUrl){
        //获得access token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
            try {
                wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    String openId=wxMpOAuth2AccessToken.getOpenId();
            return "redirect:"+returnUrl+"?openId="+openId;
    }

    @GetMapping("qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl){
    String url=projectUrlConfig.getWeChatOpenAuthorizeUrl();
    String redirectUrl=wxOpenService.buildQrConnectUrl(url,WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
    return "redirect:"+redirectUrl;
    }*//*

    @GetMapping("qrUserInfo")
    //用户信息
    public String qrUserInfo(@RequestParam("code") String code,@RequestParam("returnUrl") String returnUrl){
        //获得access token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        String openId=wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openId="+openId;
    }*/

}
