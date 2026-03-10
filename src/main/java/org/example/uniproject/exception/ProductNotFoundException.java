package org.example.uniproject.exception;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {
    private final Integer id;

    public ProductNotFoundException(Integer id) {
        super("Product with id=%d not found".formatted(id));
        this.id = id;
    }
}
