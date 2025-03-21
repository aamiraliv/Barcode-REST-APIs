package com.microservice.product_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WishlistItems> wishlistItems;

    public Wishlist(Long userId) {
        this.userId = userId;
    }

    public Wishlist() {
    }

    public Wishlist(Long id, List<WishlistItems> wishlistItems, Long userId) {
        this.id = id;
        this.wishlistItems = wishlistItems;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<WishlistItems> getWishlistItems() {
        return wishlistItems;
    }

    public void setWishlistItems(List<WishlistItems> wishlistItems) {
        this.wishlistItems = wishlistItems;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
