package org.example.uniproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.uniproject.dto.*;
import org.example.uniproject.entity.User;
import org.example.uniproject.exception.UserNotFoundException;
import org.example.uniproject.mapper.UserMapper;
import org.example.uniproject.repository.UserRepository;
import org.example.uniproject.repository.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toResponse(user))
                .toList();
    }

    public PageResponse<UserResponseDto> findAll(Pageable pageable, UserFilter filter) {

        var spec = UserSpecification.userFilter(filter);

        Page<User> page = userRepository.findAll(spec, pageable);

        List<UserResponseDto> content = page.getContent().stream()
                .map(user -> userMapper.toResponse(user))
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

    public UserResponseDto findById(Integer id) {

        log.debug("Request to find user by id={}", id);

        return userRepository.findById(id)
                .map(user -> userMapper.toResponse(user))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {

        log.info("Creating user with username={}", createUserRequestDto.getUsername());

        return Optional.ofNullable(createUserRequestDto)
                .map(userDto -> userMapper.toEntity(userDto))
                .map(user -> userRepository.save(user))
                .map(user -> userMapper.toResponse(user))
                .orElseThrow();
    }

    @Transactional
    public UserResponseDto updateUser(Integer id, UpdateUserRequestDto updateUserRequestDto) {

        log.info("Updating user id={}", id);

        return userRepository.findById(id)
                .map(user -> userMapper.updateEntity(updateUserRequestDto, user))
                .map(user -> userRepository.saveAndFlush(user))
                .map(user -> userMapper.toResponse(user))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public void deleteUser(Integer id) {

        log.warn("Deleting user id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }
}

