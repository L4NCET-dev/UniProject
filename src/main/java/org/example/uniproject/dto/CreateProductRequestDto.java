package org.example.uniproject.dto;

import jakarta.validation.constraints.*;
import lombok.Value;
import java.math.BigDecimal;

@Value
public class CreateProductRequestDto {
    @NotBlank
    @Size(max = 128)
    String name;

    @NotNull
    @DecimalMin(value = "0.00")
    BigDecimal price;

    @NotNull
    Boolean active;

    @NotNull
    @Min(1)
    Integer categoryId;
}
