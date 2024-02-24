package com.codewithProjects.ecom.dto;

import lombok.Data;

import java.security.PrivateKey;

@Data
public class PlaceOrderDto {

    private Long userId;

    private String address;

    private String orderDescription;
}
