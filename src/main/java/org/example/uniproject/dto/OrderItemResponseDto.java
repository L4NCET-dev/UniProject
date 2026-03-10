package org.example.uniproject.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderItemResponseDto {
    Integer id;
    Integer productId;
    String productName;
    Integer quantity;
    BigDecimal unitPrice;
    BigDecimal lineTotal;
}
