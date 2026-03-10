package org.example.uniproject.mapper;

import org.example.uniproject.dto.OrderItemResponseDto;
import org.example.uniproject.dto.OrderResponseDto;
import org.example.uniproject.entity.Order;
import org.example.uniproject.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDto toResponse(Order order, List<OrderItem> items) {
        List<OrderItemResponseDto> itemDtos = items.stream()
                .map(this::toItemResponse)
                .toList();

        BigDecimal total = itemDtos.stream()
                .map(OrderItemResponseDto::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderResponseDto(
                order.getId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUser().getId(),
                order.getUser().getUsername(),
                itemDtos,
                total
        );
    }

    private OrderItemResponseDto toItemResponse(OrderItem item) {
        BigDecimal lineTotal = item.getUnitPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new OrderItemResponseDto(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                lineTotal
        );
    }
}
