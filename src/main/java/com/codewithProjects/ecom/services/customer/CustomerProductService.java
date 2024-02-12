package com.codewithProjects.ecom.services.customer;

import com.codewithProjects.ecom.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {
    List<ProductDto> searchProductByTitle(String title);

    List<ProductDto> getAllProducts();



}
