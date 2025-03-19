package com.microservice.order_service.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderItem> orderItems;

    private String orderStatus;

    private LocalDateTime orderDate;  // Timestamp field

    // Automatically set the timestamp before saving the order
    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }
}
