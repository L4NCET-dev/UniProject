package org.example.uniproject.dto;

import lombok.Value;

@Value
public class JwtAuthenticationDto {
    String token;
    String refreshToken;
}
