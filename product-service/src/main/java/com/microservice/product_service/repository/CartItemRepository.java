package com.microservice.product_service.repository;

import com.microservice.product_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem , Long> {
    Optional<CartItem> findByCartIdAndProductId(Long id, Long productId);
    void deleteByCartId(Long id);
}
