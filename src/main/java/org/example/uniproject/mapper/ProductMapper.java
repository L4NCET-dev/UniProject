package org.example.uniproject.mapper;

import org.example.uniproject.dto.ProductResponseDto;
import org.example.uniproject.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.isActive(),
                product.getProductCategory().getId(),
                product.getProductCategory().getName()
        );
    }
}
