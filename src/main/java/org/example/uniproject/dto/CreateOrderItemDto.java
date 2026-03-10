package org.example.uniproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CreateOrderItemDto {
    @NotNull
    @Min(1)
    Integer productId;

    @NotNull
    @Min(1)
    Integer quantity;
}
