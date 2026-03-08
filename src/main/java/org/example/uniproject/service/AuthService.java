package org.example.uniproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.uniproject.config.security.jwt.JwtService;
import org.example.uniproject.dto.JwtAuthenticationDto;
import org.example.uniproject.dto.RefreshTokenDto;
import org.example.uniproject.dto.UserCredentialsDto;
import org.example.uniproject.entity.User;
import org.example.uniproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationDto signIn(UserCredentialsDto credentials) {

        log.info("User sign-in attempt: username={}", credentials.getUsername());

        // 1. достаём пользователя (через уже реализованный loadUserByUsername)
        UserDetails userDetails = loadUserByUsername(credentials.getUsername());

        // 2. сверяем пароль
        if (!passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())) {
            // Важно: кидаем BadCredentialsException — её легко обработать в GlobalExceptionHandler
            throw new BadCredentialsException("Invalid username or password");
        }

        // 3. генерируем пару токенов
        return jwtService.generateAuthToken(userDetails.getUsername());
    }

    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        log.info("Refresh token attempt");

        // 1. проверяем refresh токен
        if (!jwtService.validateJwtToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        // 2. достаём username из refresh
        String username = jwtService.getUsernameFromToken(refreshToken);

        // 3. генерим новый access, refresh оставляем прежним
        return jwtService.refreshBaseToken(username, refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username %s not found".formatted(username)));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
