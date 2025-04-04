package com.microservice.order_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {
    private Long productId;
    private int quantity;
    private String deliveryStatus;
    private ProductResponse productResponse;

}
