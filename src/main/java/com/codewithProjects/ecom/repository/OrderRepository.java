package com.codewithProjects.ecom.repository;

import com.codewithProjects.ecom.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
