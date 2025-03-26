package com.microservice.api_gateway.config;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (request.getURI().getPath().startsWith("/api/auth")) {
            return chain.filter(exchange);
        }

        List<String> cookies = request.getHeaders().get("Cookie");
        if (cookies == null || cookies.isEmpty()) {
            return unauthorizedResponse(exchange);
        }

        String jwtToken = jwtUtil.extractTokenFromCookie(cookies);
        if (jwtToken == null) {
            return unauthorizedResponse(exchange);
        }

        if (!jwtUtil.validateToken(jwtToken)) {
            return unauthorizedResponse(exchange);
        }

        Claims claims = jwtUtil.extractClaims(jwtToken);
        String role = claims.get("role", String.class);
        String path = request.getURI().getPath();

        if (path.startsWith("/api/admin") && !"ADMIN".equals(role)) {
            return forbiddenResponse(exchange);
        }

        return chain.filter(exchange);
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
