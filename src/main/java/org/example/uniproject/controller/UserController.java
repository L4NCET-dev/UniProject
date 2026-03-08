package org.example.uniproject.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.uniproject.dto.*;
import org.example.uniproject.service.UserService;
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
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> findAllWithoutPagination() {
        return userService.findAll();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<UserResponseDto> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable, UserFilter filter) {
        return userService.findAll(pageable, filter);
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable @Min(1) Integer id) {
        return userService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(@RequestBody @Valid CreateUserRequestDto createUserRequestDto) {
        return userService.createUser(createUserRequestDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto update(@PathVariable @Min(1) Integer id, @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        return userService.updateUser(id, updateUserRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable @Min(1) Integer id) {
        userService.deleteUser(id);
    }

}
