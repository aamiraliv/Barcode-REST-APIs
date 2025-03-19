package com.microservice.product_service.repository;

import com.microservice.product_service.model.WishlistItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface WishlistItemRepository extends JpaRepository<WishlistItems , Long> {

    boolean existsByWishlistIdAndProductId(Long id, Long productId);
    Optional<WishlistItems> findByWishlistIdAndProductId(Long wishlistId, Long productId);
}
