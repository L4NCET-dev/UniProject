package org.example.uniproject.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {


    //Jwt
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
//        http
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session
//                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST, "/api/auth/sign-in").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //HttpBasic
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // REST API обычно без CSRF (мы не работаем через браузерные формы)
                .csrf(csrf -> csrf.disable())

                // Делаем API stateless — без HTTP-сессий
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // регистрация пользователя — можно сделать доступной без авторизации
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        // пагинация/поиск пользователей — только админ
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                        // всё остальное — только аутентифицированным
                        .anyRequest().authenticated()
                )

                // Включаем HTTP Basic (логин/пароль в заголовке Authorization)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


}

