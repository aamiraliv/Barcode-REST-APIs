package com.microservice.api_gateway.config;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        System.out.println("Gateway forwarding request: " + request.getURI());
        System.out.println("API Gateway - Authorization Header: " + request.getHeaders().get("Authorization"));

        System.out.println("Headers: " + request.getHeaders());

        if (request.getURI().getPath().startsWith("/api/auth")) {
            return chain.filter(exchange);
        }

        List<String> cookies = request.getHeaders().get("Cookie");
        if (cookies == null || cookies.isEmpty()) {
            return unauthorizedResponse(exchange);
        }

        String jwtToken = jwtUtil.extractTokenFromCookie(cookies);
        System.out.println("Extracted JWT: " + jwtToken);
        if (jwtToken == null) {
            return unauthorizedResponse(exchange);
        }

        if (!jwtUtil.validateToken(jwtToken)) {
            return unauthorizedResponse(exchange);
        }

        Claims claims = jwtUtil.extractClaims(jwtToken);
        List<String > roles = claims.get("roles", List.class);
        String path = request.getURI().getPath();

        System.out.println("Extracted Claims: " + claims);
        System.out.println("Extracted Role: " + roles);

        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(), null, authorities
        );


        if (path.startsWith("/api/admin") && (roles == null || !roles.contains("ADMIN"))) {
            return forbiddenResponse(exchange);
        }

        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private Mono<Void> forbiddenResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}
