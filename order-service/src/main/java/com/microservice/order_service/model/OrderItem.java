package com.microservice.order_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private int quantity;

    private String deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
