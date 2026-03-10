package org.example.uniproject.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.example.uniproject.dto.JwtAuthenticationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtService {

    @Value("154a38f18aea4b60f216d2b3e0601433438741c7b29cf1b1b334b61ab6ee414e")
    private String jwtSecret;


    public JwtAuthenticationDto generateAuthToken(String username) {
        return new JwtAuthenticationDto(generateJwtToken(username), generateRefreshToken(username));
    }

    public JwtAuthenticationDto refreshBaseToken(String username, String refreshToken) {
        return new JwtAuthenticationDto(generateJwtToken(username), refreshToken);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSingInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("ExpiredJwtException ", expEx);
        } catch (UnsupportedJwtException expEx) {
            log.error("UnsupportedJwtException ", expEx);
        } catch (MalformedJwtException expEx) {
            log.error("MalformedJwtException ", expEx);
        } catch (SecurityException expEx) {
            log.error("SecurityException ", expEx);
        } catch (Exception e) {
            log.error("Invalid token ", e);
        }
        return false;
    }

    private String generateJwtToken(String username) {
        Date date = Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(username)
                .expiration(date)
                .signWith(getSingInKey())
                .compact();
    }

    private String generateRefreshToken(String username) {
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(username)
                .expiration(date)
                .signWith(getSingInKey())
                .compact();
    }

    private SecretKey getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
