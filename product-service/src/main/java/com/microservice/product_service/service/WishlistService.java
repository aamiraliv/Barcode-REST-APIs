package com.microservice.product_service.service;

import com.microservice.product_service.model.Product;
import com.microservice.product_service.model.Wishlist;
import com.microservice.product_service.model.WishlistItems;
import com.microservice.product_service.repository.ProductRepository;
import com.microservice.product_service.repository.WishlistItemRepository;
import com.microservice.product_service.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Wishlist addToWishlist(Long userId, Long productId) {

        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> wishlistRepository.save(new Wishlist(userId)));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (wishlistItemRepository.existsByWishlistIdAndProductId(wishlist.getId(), productId)) {
            throw new RuntimeException("Item already in the wishlist");
        }

        WishlistItems wishlistItem = new WishlistItems();
        wishlistItem.setWishlist(wishlist);
        wishlistItem.setProduct(product);
        wishlistItemRepository.save(wishlistItem);

        return wishlist;
    }

    public ResponseEntity<Map<String, Object>> removeFromWishlist(Long userId, Long productId) {

        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        WishlistItems wishlistItem = wishlistItemRepository.findByWishlistIdAndProductId(wishlist.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in wishlist"));

        wishlistItemRepository.delete(wishlistItem);

        Map<String ,Object> response = new HashMap<>();
        response.put("success",true);
        return ResponseEntity.ok(response);
    }

}
