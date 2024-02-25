package com.codewithProjects.ecom.repository;

import com.codewithProjects.ecom.entity.Order;
import com.codewithProjects.ecom.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserIdAndOrderStatus(Long UserId, OrderStatus orderStatus);
    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);
}
