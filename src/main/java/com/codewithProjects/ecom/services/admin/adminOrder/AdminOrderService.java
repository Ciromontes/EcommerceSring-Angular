package com.codewithProjects.ecom.services.admin.adminOrder;

import com.codewithProjects.ecom.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {
    List<OrderDto> getAllPlacedOrders();
    public OrderDto changeOrderStatus(Long orderId, String status);
}
