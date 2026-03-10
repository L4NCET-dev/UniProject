package org.example.uniproject.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {
    private final Integer id;

    public OrderNotFoundException(Integer id) {
        super("Order with id=%d not found".formatted(id));
        this.id = id;
    }
}
