package com.food.foodorder.service.impl;

import com.food.foodorder.config.WeChatAccountConfig;
import com.food.foodorder.dto.OrderDto;
import com.food.foodorder.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 微信模板消息推送
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {
   /* @Autowired
    private WxMpService wxMpService;*/
    @Autowired
    private WeChatAccountConfig accountConfig;
    @Override
    public void orderStatus(OrderDto orderDto) {
      /*  WxMpTemplateMessage wxMpTemplateMessage=new WxMpTemplateMessage();
        wxMpTemplateMessage.setToUser(orderDto.getBuyerOpenid()); //设置推送的微信openID
        wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));//设置推送的消息模板id
       //设置推送的消息数据
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货。"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "18868812345"),
                new WxMpTemplateData("keyword3", orderDto.getOrderId()),
                new WxMpTemplateData("keyword4", orderDto.getOrderStatusEnum().getDec()),
                new WxMpTemplateData("keyword5", "￥" + orderDto.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临！")
        );
        wxMpTemplateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        }catch (WxErrorException e){

            log.error("【微信模版消息】发送失败, {}", e);
        }*/
    }
}
