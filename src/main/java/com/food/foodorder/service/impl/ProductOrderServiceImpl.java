package com.food.foodorder.service.impl;

import com.food.foodorder.common.OrderStatus;
import com.food.foodorder.common.PayStatus;
import com.food.foodorder.common.ProductStatusEnum;
import com.food.foodorder.common.ServerResponse;
import com.food.foodorder.converter.OrderMasterToOrderDtoConverter;
import com.food.foodorder.domain.OrderDetail;
import com.food.foodorder.domain.OrderMaster;
import com.food.foodorder.domain.ProductInfo;
import com.food.foodorder.dto.CartDto;
import com.food.foodorder.dto.OrderDto;
import com.food.foodorder.exception.SellException;
import com.food.foodorder.repository.OrderDetailRepository;
import com.food.foodorder.repository.OrderMasterRepository;
import com.food.foodorder.repository.ProductInfoRepository;
import com.food.foodorder.service.*;
import com.food.foodorder.utils.BigDecimalUtils;
import com.food.foodorder.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductOrderServiceImpl implements ProductOrderService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PayService payService;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private PushMessageService pushMessageService;

    @Override //查找某个订单
    public OrderDto findOneOrder(String orderId) {
       OrderMaster orderMaster=orderMasterRepository.getOne(orderId);
       if(orderMaster==null){
           throw new SellException(ProductStatusEnum.ORDER_NOT_EXIST);
       }

       //查找相关订单详情
        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderMaster.getOrderId());
       if (CollectionUtils.isEmpty(orderDetailList)){
           throw new SellException(ProductStatusEnum.ORDER_DETAIL_NOT_EXIST);
       }

       OrderDto orderDto= OrderMasterToOrderDtoConverter.convert(orderMaster);
        //装配订单主体中的List<OrderDetail>
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Override
    //某一openId的订单列表
    public Page<OrderDto> orderList(String openId,Pageable pageable) {
        //取出openId所有的相关订单
        Page<OrderMaster> orderMasters=orderMasterRepository.findByBuyerOpenid(openId,pageable);

        List<OrderDto> orderDtoList=OrderMasterToOrderDtoConverter.convert(orderMasters.getContent());

        Page page=new PageImpl(orderDtoList,pageable,orderMasters.getTotalElements());

        return page;
    }

    public Page<OrderDto> findList(Pageable pageable){
        Page<OrderMaster> orderMasters=orderMasterRepository.findAll(pageable);

        List<OrderDto> orderDtoList=OrderMasterToOrderDtoConverter.convert(orderMasters.getContent());

        Page page=new PageImpl(orderDtoList,pageable,orderMasters.getTotalElements());

        return page;
    }

    @Override
    //订单创建
    public ServerResponse<Map<String ,String>> createOrder(OrderDto orderDto) {
        //设置订单号
       String orderId= KeyUtils.generateOrderId();

        //查找订单中单个商品的价格，库存
        BigDecimal totalPrice=new BigDecimal("0");
        for (OrderDetail orderDetail:orderDto.getOrderDetailList()) {
         // 查找数据库中商品的价格及库存
            ProductInfo productInfo=productInfoRepository.findById(orderDetail.getProductId()).get();
            if(productInfo==null){
                throw new SellException(ProductStatusEnum.PRODUCT_NOT_EXIST);
            }

            //计算总价
            totalPrice=BigDecimalUtils.add(totalPrice.doubleValue(),
                    BigDecimalUtils.mul(productInfo.getProductPrice().doubleValue(),orderDetail.getProductQuantity().doubleValue()).doubleValue());

            //将商品信息装配到detail中
            BeanUtils.copyProperties(productInfo,orderDetail);
            //将订单详情写入数据库
            orderDetail.setDetailId(KeyUtils.generateOrderId());
            orderDetail.setOrderId(orderId);

            orderDetailRepository.save(orderDetail);
            orderDetailRepository.save(orderDetail);

        }

        //将订单主体写入数据库
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(totalPrice);


        orderMasterRepository.save(orderMaster);

        //减库存
       List<CartDto> cartDtoList=orderDto.getOrderDetailList().stream().
                map(e -> new CartDto(e.getProductId(),e.getProductQuantity())).
                collect(Collectors.toList());

       productService.reduceQuantity(cartDtoList);

        Map<String ,String> result=new HashMap<>();
        result.put("orderId",orderDto.getOrderId());
        //发送websocket消息
        webSocket.sendMessage("有新的订单:orderId="+orderId);

        return ServerResponse.createBySuccessMsgAndData("创建订单成功",result);
    }

    //订单完成
    @Override
    public ServerResponse<OrderDto> finish(OrderDto orderDto) {
        //判断订单状态
        if(orderDto.getOrderStatus()!= OrderStatus.NEW_ORDER.getCode()){
            log.error("【订单完成】订单状态错误：orderId={}",orderDto.getOrderId());
            throw new SellException(ProductStatusEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatus.FINISH_ORDER.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);

         OrderMaster result=orderMasterRepository.save(orderMaster);
         if(result==null){
             log.error("【订单完成】订单状态更新失败，orderMaster={}",orderMaster);
             throw new SellException(ProductStatusEnum.ORDER_STATUS_UPDATE_ERROR);
         }
         //推送微信模板消息
        pushMessageService.orderStatus(orderDto);
        return ServerResponse.createBySuccessMsgAndData("订单已完成",null);
    }

    //订单支付
    @Override
    public ServerResponse<OrderDto> paid(OrderDto orderDto) {
        //判断订单状态
        if(orderDto.getOrderStatus()!=OrderStatus.NEW_ORDER.getCode()){
            log.error("【订单支付】订单状态错误：orderId={}",orderDto.getOrderId());
            throw new SellException(ProductStatusEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(orderDto.getPayStatus()!= PayStatus.NO_PAY.getCode()){
            log.error("【订单支付】支付状态错误：orderId={}",orderDto.getOrderId());
            throw new SellException(ProductStatusEnum.PAID_STATUS_ERROR);
        }
        //更新支付状态
        OrderMaster orderMaster=new OrderMaster();
        orderDto.setPayStatus(PayStatus.PAID.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);

        //更新数据库
        OrderMaster result=orderMasterRepository.save(orderMaster);

        if(result==null){
            log.error("【订单支付】订单支付状态更新失败，orderMaster={}",orderMaster);
            throw new SellException(ProductStatusEnum.PAID_STATUS_UPDATE_ERROR);
        }
        return ServerResponse.createBySuccessMsgAndData("订单已支付",null);

    }

    @Override //订单取消
    @Transactional
    public ServerResponse cancel(OrderDto orderDto) {
        OrderMaster orderMaster=new OrderMaster();

        //判读订单状态，是新订单才可以取消
        if(orderDto.getOrderStatus()!=OrderStatus.NEW_ORDER.getCode()){
            log.error("【取消订单】订单状态不正确：orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ProductStatusEnum.ORDER_STATUS_ERROR);
        }

        //更新订单状态
        orderDto.setOrderStatus(OrderStatus.CANCEL_ORDER.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);

       OrderMaster updateResult=orderMasterRepository.save(orderMaster);
       if(updateResult==null){
           log.error("订单状态更新失败，orderMaster={}",orderMaster);
           throw new SellException(ProductStatusEnum.ORDER_STATUS_UPDATE_ERROR);
       }

        //库存返回
        List<CartDto> cartDtoList=orderDto.getOrderDetailList().stream()
                .map(e ->new CartDto(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseQuantity(cartDtoList);

        //已支付需要退款
        if(orderDto.getPayStatus()==PayStatus.PAID.getCode()){
            payService.refund(orderDto);

        }
        return ServerResponse.createBySuccessMsgAndData("订单取消成功",null);
    }


}
