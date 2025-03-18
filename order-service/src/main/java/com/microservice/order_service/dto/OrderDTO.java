package com.microservice.order_service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private Long userId;
    private String orderStatus;
    private List<OrderItemDTO> orderItems;

}
