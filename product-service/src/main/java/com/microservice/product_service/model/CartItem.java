package com.microservice.product_service.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;


    public CartItem() {
    }

    public CartItem(Long id, Cart cart, Product product, int quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(Cart cart, Product product, int quantity) {
    }


}
