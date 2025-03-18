package com.microservice.product_service.controller;


import com.microservice.product_service.model.Cart;
import com.microservice.product_service.model.CartItem;
import com.microservice.product_service.service.CartService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addProductToCart(userId, productId, quantity));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(userId, productId));
    }

    @PutMapping("/updateQuantity")
    public ResponseEntity<CartItem> updateQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity){
        return ResponseEntity.ok(cartService.updateQuantity(userId,productId,quantity));
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> GetCartItems(@RequestParam Long userId){
        return ResponseEntity.ok(cartService.getCartItemsByUserId(userId));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Map<String, String>> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Cart cleared successfully"));
    }
}
