package org.example.uniproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.uniproject.dto.*;
import org.example.uniproject.entity.*;
import org.example.uniproject.exception.OrderNotFoundException;
import org.example.uniproject.exception.ProductNotFoundException;
import org.example.uniproject.exception.UserNotFoundException;
import org.example.uniproject.mapper.OrderMapper;
import org.example.uniproject.repository.OrderItemRepository;
import org.example.uniproject.repository.OrderRepository;
import org.example.uniproject.repository.ProductRepository;
import org.example.uniproject.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponseDto create(CreateOrderRequestDto dto) {
        User user = getCurrentUserEntity();

        Order orderToSave = Order.builder()
                .user(user)
                .status(Status.NEW)
                .createdAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(orderToSave);

        List<OrderItem> items = dto.getItems().stream()
                .map(i -> {
                    Product product = productRepository.findById(i.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(i.getProductId()));

                    return OrderItem.builder()
                            .order(savedOrder)
                            .product(product)
                            .quantity(i.getQuantity())
                            .unitPrice(product.getPrice())
                            .build();
                })
                .toList();

        orderItemRepository.saveAll(items);

        return orderMapper.toResponse(savedOrder, items);
    }

    public OrderResponseDto findById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        checkAccess(order);

        List<OrderItem> items = orderItemRepository.findAllByOrderId(order.getId());
        return orderMapper.toResponse(order, items);
    }

    public PageResponse<OrderResponseDto> findAll(Pageable pageable) {
        User current = getCurrentUserEntity();
        boolean isAdmin = current.getRole() == Role.ADMIN;

        Page<Order> page = isAdmin
                ? orderRepository.findAll(pageable)
                : orderRepository.findAll((root, query, cb) ->
                cb.equal(root.get("user").get("id"), current.getId()), pageable);

        List<OrderResponseDto> content = page.getContent().stream()
                .map(o -> {
                    List<OrderItem> items = orderItemRepository.findAllByOrderId(o.getId());
                    return orderMapper.toResponse(o, items);
                })
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

    @Transactional
    public OrderResponseDto updateStatus(Integer id, UpdateOrderStatusDto dto) {
        User current = getCurrentUserEntity();
        if (current.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only ADMIN can change order status");
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        order.setStatus(dto.getStatus());

        Order savedOrder = orderRepository.saveAndFlush(order);

        List<OrderItem> items = orderItemRepository.findAllByOrderId(savedOrder.getId());
        return orderMapper.toResponse(savedOrder, items);
    }

    private User getCurrentUserEntity() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(-1));
    }

    private void checkAccess(Order order) {
        User current = getCurrentUserEntity();
        if (current.getRole() == Role.ADMIN) return;

        if (!order.getUser().getId().equals(current.getId())) {
            throw new AccessDeniedException("Access denied to чужой order");
        }
    }
}

