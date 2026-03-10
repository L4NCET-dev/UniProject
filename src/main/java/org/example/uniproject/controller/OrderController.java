package org.example.uniproject.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.uniproject.dto.*;
import org.example.uniproject.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto create(@RequestBody @Valid CreateOrderRequestDto dto) {
        return orderService.create(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<OrderResponseDto> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto findById(@PathVariable @Min(1) Integer id) {
        return orderService.findById(id);
    }

    @PatchMapping(value = "/{id}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto updateStatus(@PathVariable @Min(1) Integer id,
                                         @RequestBody @Valid UpdateOrderStatusDto dto) {
        return orderService.updateStatus(id, dto);
    }
}
