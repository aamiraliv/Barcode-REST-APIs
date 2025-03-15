//package com.microservice.api_gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers("/api/auth/**").permitAll()// Allow public endpoints
//                        .pathMatchers("/actuator").permitAll()
//                        .anyExchange().authenticated()  // Secure other endpoints
//                )
//                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) // Disable default login
//                .formLogin(ServerHttpSecurity.FormLoginSpec::disable) // Disable login page redirection
//                .logout(ServerHttpSecurity.LogoutSpec::disable); // Disable logout handling
//
//        return http.build();
//    }
//}
