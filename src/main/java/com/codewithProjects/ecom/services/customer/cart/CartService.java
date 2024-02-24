package com.codewithProjects.ecom.services.customer.cart;

import com.codewithProjects.ecom.dto.AddProductInCartDto;
import com.codewithProjects.ecom.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
    OrderDto getCartByUserId(Long userId);
    OrderDto applyCoupon(Long userId, String code);
    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);
}
