package com.food.foodorder.repository;

import com.food.foodorder.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    //分页查询某用户的订单
    Page<OrderMaster> findByBuyerOpenid(String openId, Pageable pageable);

}