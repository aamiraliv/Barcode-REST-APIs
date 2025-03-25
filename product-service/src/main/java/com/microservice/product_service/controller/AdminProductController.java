package com.microservice.product_service.controller;

import com.microservice.product_service.dto.ProductDTO;
import com.microservice.product_service.service.ImageUploadService;
import com.microservice.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ImageUploadService uploadService;

    @PostMapping
    public ResponseEntity<Map<String ,Object >> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO productDTO1 =service.createProduct(productDTO);
        Map<String ,Object> response = new HashMap<>();
        response.put("data",productDTO1);
        response.put("success",true);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String ,Object >> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        service.updateProduct(id, productDTO);
        return ResponseEntity.ok(Collections.singletonMap("success",true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String ,Boolean >> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.ok(Collections.singletonMap("success",true));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile file){
        try {
            String image_url = uploadService.uploadImage(file);
            return ResponseEntity.ok().body(Map.of("image_url",image_url));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Image upload failed"));
        }
    }
}
