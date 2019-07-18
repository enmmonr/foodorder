package com.food.foodorder.repository;

import com.food.foodorder.domain.OrderDetail;
import com.food.foodorder.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {


    List<OrderDetail> findByOrderId(List<String> orderId);
    List<OrderDetail> findByOrderId(String orderId);

}
