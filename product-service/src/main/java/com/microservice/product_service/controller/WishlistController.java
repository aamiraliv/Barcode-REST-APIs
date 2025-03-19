package com.microservice.product_service.controller;

import com.microservice.product_service.model.Wishlist;
import com.microservice.product_service.repository.WishlistRepository;
import com.microservice.product_service.service.WishlistService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService service;

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addToWishlist(@RequestParam Long userId,@RequestParam Long productId){
        return  ResponseEntity.ok(service.addToWishlist(userId,productId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String ,Object>> removeFromWishlist(@RequestParam Long userId,@RequestParam Long productId ){
        return service.removeFromWishlist(userId, productId);
    }
}
