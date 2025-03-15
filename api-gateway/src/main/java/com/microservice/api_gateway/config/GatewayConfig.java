//package com.microservice.api_gateway.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//
//    private final AuthenticationFilter authenticationFilter;
//
//    public GatewayConfig(AuthenticationFilter authenticationFilter) {
//        this.authenticationFilter = authenticationFilter;
//    }
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("user-service", r -> r.path("/api/users/**")
//                        .filters(f -> f.filter(authenticationFilter)) // Apply filter here
//                        .uri("lb://USER-SERVICE"))
//                .route("product-service", r -> r.path("/api/products/**")
//                        .filters(f -> f.filter(authenticationFilter)) // Apply filter here
//                        .uri("lb://PRODUCT-SERVICE"))
//                .route("order-service", r -> r.path("/api/orders/**")
//                        .filters(f -> f.filter(authenticationFilter)) // Apply filter here
//                        .uri("lb://ORDER-SERVICE"))
//                .route("auth-service", r -> r.path("/api/auth/**") // No filter for auth
//                        .uri("lb://AUTH-SERVICE"))
//                .build();
//    }
//}
