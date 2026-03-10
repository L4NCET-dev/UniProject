package org.example.uniproject.mapper;

import org.example.uniproject.dto.CategoryResponseDto;
import org.example.uniproject.dto.CreateCategoryRequestDto;
import org.example.uniproject.entity.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public ProductCategory toEntity(CreateCategoryRequestDto dto) {
        return ProductCategory.builder()
                .name(dto.getName())
                .build();
    }

    public CategoryResponseDto toResponse(ProductCategory entity) {
        return new CategoryResponseDto(entity.getId(), entity.getName());
    }
}
