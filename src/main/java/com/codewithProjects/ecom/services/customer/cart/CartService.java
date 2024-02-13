package com.codewithProjects.ecom.services.customer.cart;

import com.codewithProjects.ecom.dto.AddProductInCartDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
}
