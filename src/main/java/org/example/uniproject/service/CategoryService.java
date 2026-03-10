package org.example.uniproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.uniproject.dto.CategoryResponseDto;
import org.example.uniproject.dto.CreateCategoryRequestDto;
import org.example.uniproject.entity.ProductCategory;
import org.example.uniproject.exception.CategoryNotFoundException;
import org.example.uniproject.mapper.CategoryMapper;
import org.example.uniproject.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final ProductCategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    public CategoryResponseDto findById(Integer id) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponseDto create(CreateCategoryRequestDto dto) {
        log.info("Creating category name={}", dto.getName());
        ProductCategory entity = categoryMapper.toEntity(dto);
        return categoryMapper.toResponse(categoryRepository.save(entity));
    }

    @Transactional
    public void delete(Integer id) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.delete(category);
    }
}
