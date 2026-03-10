package org.example.uniproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.uniproject.dto.*;
import org.example.uniproject.entity.Product;
import org.example.uniproject.entity.ProductCategory;
import org.example.uniproject.exception.CategoryNotFoundException;
import org.example.uniproject.exception.ProductNotFoundException;
import org.example.uniproject.mapper.ProductMapper;
import org.example.uniproject.repository.ProductCategoryRepository;
import org.example.uniproject.repository.ProductRepository;
import org.example.uniproject.repository.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductResponseDto> findAllWithoutPagination() {
        return productRepository.findAll().stream().map(productMapper::toResponse).toList();
    }

    public PageResponse<ProductResponseDto> findAll(Pageable pageable, ProductFilter filter) {
        var spec = ProductSpecification.filter(filter);
        Page<Product> page = productRepository.findAll(spec, pageable);

        List<ProductResponseDto> content = page.getContent().stream()
                .map(productMapper::toResponse)
                .toList();

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public ProductResponseDto findById(Integer id) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toResponse(p);
    }

    @Transactional
    public ProductResponseDto create(CreateProductRequestDto dto) {
        ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));

        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .active(dto.getActive())
                .productCategory(category)
                .build();

        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDto update(Integer id, UpdateProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getActive() != null) product.setActive(dto.getActive());

        if (dto.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));
            product.setProductCategory(category);
        }

        return productMapper.toResponse(productRepository.saveAndFlush(product));
    }

    @Transactional
    public void delete(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
