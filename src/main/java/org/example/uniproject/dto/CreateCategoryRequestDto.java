package org.example.uniproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CreateCategoryRequestDto {
    @NotBlank
    @Size(max = 128)
    String name;
}
