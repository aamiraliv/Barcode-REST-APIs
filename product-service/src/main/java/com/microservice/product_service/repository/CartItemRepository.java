package com.microservice.product_service.repository;

import com.microservice.product_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem , Long> {
    Optional<CartItem> findByCartIdAndProductId(Long id, Long productId);
    void deleteByCartId(Long id);
}
