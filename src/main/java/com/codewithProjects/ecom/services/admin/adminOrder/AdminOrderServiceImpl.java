package com.codewithProjects.ecom.services.admin.adminOrder;

import com.codewithProjects.ecom.dto.OrderDto;
import com.codewithProjects.ecom.entity.Order;
import com.codewithProjects.ecom.enums.OrderStatus;
import com.codewithProjects.ecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AdminOrderServiceImpl implements AdminOrderService {
    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlacedOrders(){

        List<Order> orderList = orderRepository.
                findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered));

        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());



    }
    public OrderDto changeOrderStatus(Long orderId, String status){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            if(Objects.equals(status, "Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            }else if(Objects.equals(status, "Delivered")){
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }



}


/*Primero, le dices al ayudante el número de pedido (orderId) y el nuevo estado (status) que quieres cambiar.
El ayudante va a buscar en la lista de todos los pedidos (orderRepository.findById(orderId)) para encontrar el pedido con ese número.
Si el ayudante encuentra el pedido (if(optionalOrder.isPresent())), entonces hace lo siguiente:
Toma el pedido (Order order = optionalOrder.get()).
Si el estado que le diste es “Enviado” (if(Objects.equals(status, "Shipped"))), entonces cambia el estado del pedido a “Enviado” (order.setOrderStatus(OrderStatus.Shipped)).
Si el estado que le diste es “Entregado” (else if(Objects.equals(status, "Delivered"))), entonces cambia el estado del pedido a “Entregado” (order.setOrderStatus(OrderStatus.Delivered)).
Después de cambiar el estado del pedido, el ayudante guarda el pedido actualizado en la lista de pedidos (orderRepository.save(order)).
Finalmente, el ayudante te devuelve el pedido actualizado (return orderRepository.save(order).getOrderDto()).
Si el ayudante no puede encontrar el pedido con el número que le diste, entonces te devuelve nada (return null).*/

