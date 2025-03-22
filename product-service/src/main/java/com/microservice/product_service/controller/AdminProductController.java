package com.microservice.product_service.controller;

import com.microservice.product_service.dto.ProductDTO;
import com.microservice.product_service.repository.ProductRepository;
import com.microservice.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return service.createProduct(productDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
