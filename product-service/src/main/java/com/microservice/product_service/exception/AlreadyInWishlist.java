package com.microservice.product_service.exception;


public class AlreadyInWishlist extends RuntimeException{
    public AlreadyInWishlist(String message) {
        super(message);
    }
}
