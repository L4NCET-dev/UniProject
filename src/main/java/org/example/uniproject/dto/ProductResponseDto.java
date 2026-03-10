package org.example.uniproject.dto;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class ProductResponseDto {
    Integer id;
    String name;
    BigDecimal price;
    Boolean active;
    Integer categoryId;
    String categoryName;
}
