package org.example.uniproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

import java.util.List;

@Value
public class CreateOrderRequestDto {
    @NotEmpty
    List<@Valid CreateOrderItemDto> items;
}
