package org.example.uniproject.dto;

import lombok.Value;

@Value
public class ProductFilter {
    Integer categoryId;
    Boolean active;
    String name;
}
