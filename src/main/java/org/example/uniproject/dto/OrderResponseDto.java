package org.example.uniproject.dto;

import lombok.Value;
import org.example.uniproject.entity.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class OrderResponseDto {
    Integer id;
    Status status;
    LocalDateTime createdAt;

    Integer userId;
    String username;

    List<OrderItemResponseDto> items;
    BigDecimal total;
}
