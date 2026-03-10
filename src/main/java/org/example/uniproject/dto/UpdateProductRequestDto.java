package org.example.uniproject.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Value;
import java.math.BigDecimal;

@Value
public class UpdateProductRequestDto {
    @Size(max = 128)
    String name;

    @DecimalMin(value = "0.00")
    BigDecimal price;

    Boolean active;

    @Min(1)
    Integer categoryId;
}
