package com.microservice.product_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems;

    public Cart() {
    }

    public Cart(Long id, Long userId, List<CartItem> cartItems) {
        this.id = id;
        this.userId = userId;
        this.cartItems = cartItems;
    }
}
