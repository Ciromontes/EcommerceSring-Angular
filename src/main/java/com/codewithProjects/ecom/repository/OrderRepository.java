package com.codewithProjects.ecom.repository;

import com.codewithProjects.ecom.entity.Order;
import com.codewithProjects.ecom.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserIdAndOrderStatus(Long UserId, OrderStatus orderStatus);
}
