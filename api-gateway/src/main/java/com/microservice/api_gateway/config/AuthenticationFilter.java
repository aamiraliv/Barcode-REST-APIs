//package com.microservice.api_gateway.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpCookie;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@Order(Ordered.LOWEST_PRECEDENCE)
//public class AuthenticationFilter implements GatewayFilter {
//
//    private static final List<String> openEndpoints = List.of("/api/auth/", "/eureka", "/actuator");
//    private static final String COOKIE_NAME = "jwt";
//    @Value("${JWT_SECRET_KEY}")
//    private String SECRET_KEY;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String requestPath = exchange.getRequest().getPath().toString();
//
//
//        if (openEndpoints.stream().anyMatch(requestPath::startsWith)) {
//            return chain.filter(exchange);
//        }
//
//
//        String token = getTokenFromCookies(exchange);
//        if (token == null || !isValidToken(token)) {
//            return onError(exchange, "Invalid or missing authorization token", HttpStatus.UNAUTHORIZED);
//        }
//
//
//        return chain.filter(
//                exchange.mutate()
//                        .request(r -> r.headers(h -> h.set(HttpHeaders.AUTHORIZATION, "Bearer " + token)))
//                        .build()
//        );
//    }
//
//    private String getTokenFromCookies(ServerWebExchange exchange) {
//        return Optional.ofNullable(exchange.getRequest().getCookies().getFirst(COOKIE_NAME))
//                .map(HttpCookie::getValue)
//                .orElse(null);
//    }
//
//    private boolean isValidToken(String token) {
//        try {
//            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
//            Claims claims = Jwts.parser()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
//        exchange.getResponse().setStatusCode(status);
//        return exchange.getResponse().setComplete();
//    }
//}
