package org.example.uniproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.uniproject.dto.JwtAuthenticationDto;
import org.example.uniproject.dto.RefreshTokenDto;
import org.example.uniproject.dto.UserCredentialsDto;
import org.example.uniproject.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationDto signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        return authService.signIn(userCredentialsDto);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        return authService.refreshToken(refreshTokenDto);
    }
}

