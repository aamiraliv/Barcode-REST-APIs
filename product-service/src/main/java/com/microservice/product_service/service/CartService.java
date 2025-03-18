package com.microservice.product_service.service;


import com.microservice.product_service.model.Cart;
import com.microservice.product_service.model.CartItem;
import com.microservice.product_service.model.Product;
import com.microservice.product_service.repository.CartItemRepository;
import com.microservice.product_service.repository.CartRepository;
import com.microservice.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);

        Cart cart = cartOptional.orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()){
            throw new RuntimeException("product not found");
        }
        Product product = optionalProduct.get();


        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),productId);
        if (optionalCartItem.isPresent()){
            CartItem cartItem= optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
            cartItemRepository.save(cartItem);
        }else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setProduct(product);
            cartItemRepository.save(cartItem);
        }

        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(Long userId, Long productId) {


        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if (cartOptional.isPresent()){
            Cart cart = cartOptional.get();
            cart.getCartItems().removeIf(item->item.getProduct().getId().equals(productId));
            return cartRepository.save(cart);
        }
        return null;
    }

    public CartItem updateQuantity(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("cart not found for user id"));

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("product not found"));

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),productId);

        CartItem cartItem;
        if (optionalCartItem.isPresent()){
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }else {
            cartItem=new CartItem(cart,product,quantity);
        }
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemsByUserId(Long userId) {
        Optional<Cart> optionalCart= cartRepository.findByUserId(userId);
        return optionalCart.map(Cart::getCartItems).orElse(Collections.emptyList());
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("cart not found"));
        cartItemRepository.deleteByCartId(cart.getId());
    }
}
