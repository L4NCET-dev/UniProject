package org.example.uniproject.exception;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends RuntimeException {
    private final Integer id;

    public CategoryNotFoundException(Integer id) {
        super("Category with id=%d not found".formatted(id));
        this.id = id;
    }
}
