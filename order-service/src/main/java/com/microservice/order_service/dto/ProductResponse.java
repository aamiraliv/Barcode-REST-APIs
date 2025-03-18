package com.microservice.order_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private String image_url;
}
