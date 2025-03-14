package com.microservice.product_service.repository;

import com.microservice.product_service.dto.ProductDTO;
import com.microservice.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryContainingIgnoreCase(String category);
    List<Product> findTop10ByOrderBySalesDesc();
//    List<Product> findByCategory(String category);
}
