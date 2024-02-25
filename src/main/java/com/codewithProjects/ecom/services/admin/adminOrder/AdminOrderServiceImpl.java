package com.codewithProjects.ecom.services.admin.adminOrder;

import com.codewithProjects.ecom.dto.OrderDto;
import com.codewithProjects.ecom.entity.Order;
import com.codewithProjects.ecom.enums.OrderStatus;
import com.codewithProjects.ecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AdminOrderServiceImpl implements AdminOrderService {
    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlaceOrders(){

        List<Order> orderList = orderRepository.
                findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered));

        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());

    }

}
