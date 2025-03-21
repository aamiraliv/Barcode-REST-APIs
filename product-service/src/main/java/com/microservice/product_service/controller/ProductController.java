package com.microservice.product_service.controller;


import com.microservice.product_service.dto.ProductDTO;
import com.microservice.product_service.dto.ProductSalesUpdateRequest;
import com.microservice.product_service.model.Product;


import com.microservice.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOS = service.getAllProducts();
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

//    @GetMapping("/{category}")
//    public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable String category){
//        return ResponseEntity.ok(service.getProductByCategory(category));
//    }

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

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchBy(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category
    ){
        List<Product> products = service.searchBy(name,category);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/mostSelling")
    public ResponseEntity<List<Product>> topProducts(){
        return ResponseEntity.ok(service.topProducts());
    }


    @PutMapping ("/update-sales")
    public void updateProductSales(@RequestBody List<ProductSalesUpdateRequest> salesUpdateRequests){
        service.updateProductSales(salesUpdateRequests);
    }
}
