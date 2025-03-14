package com.microservice.product_service.service;

import com.microservice.product_service.dto.ProductDTO;
import com.microservice.product_service.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperService {

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO convertToDTO(Product product){
        return modelMapper.map(product,ProductDTO.class);
    }

    public Product toEntity(ProductDTO productDTO){
        return modelMapper.map(productDTO,Product.class);
    }
}
