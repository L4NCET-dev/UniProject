package org.example.uniproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.example.uniproject.entity.Status;

@Value
public class UpdateOrderStatusDto {
    @NotNull
    Status status;
}
