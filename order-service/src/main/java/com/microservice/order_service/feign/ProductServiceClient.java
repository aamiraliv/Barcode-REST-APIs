package com.microservice.order_service.feign;


import com.microservice.order_service.dto.ProductResponse;
import com.microservice.order_service.dto.ProductSalesUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductServiceClient {

    @PutMapping("/api/products/update-sales")
    void updateProductSales(@RequestBody List<ProductSalesUpdateRequest> salesUpdateRequests);

    @DeleteMapping("/api/products/cart/clear/{userId}")
    void clearCart(@PathVariable Long userId);

    @GetMapping("/api/products/{productId}")
    ProductResponse getProductById(@PathVariable Long productId);
}
