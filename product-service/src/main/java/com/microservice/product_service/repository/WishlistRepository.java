package com.microservice.product_service.repository;

import com.microservice.product_service.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WishlistRepository extends JpaRepository<Wishlist , Long> {
    Optional<Wishlist> findByUserId(Long userId);
}
