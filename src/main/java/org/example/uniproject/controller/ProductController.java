package org.example.uniproject.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.uniproject.dto.*;
import org.example.uniproject.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> findAllWithoutPagination() {
        return productService.findAllWithoutPagination();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ProductResponseDto> findAll(
            @PageableDefault(page = 0, size = 5) Pageable pageable,
            ProductFilter filter
    ) {
        return productService.findAll(pageable, filter);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto findById(@PathVariable @Min(1) Integer id) {
        return productService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto create(@RequestBody @Valid CreateProductRequestDto dto) {
        return productService.create(dto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto update(@PathVariable @Min(1) Integer id,
                                     @RequestBody @Valid UpdateProductRequestDto dto) {
        return productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable @Min(1) Integer id) {
        productService.delete(id);
    }
}
