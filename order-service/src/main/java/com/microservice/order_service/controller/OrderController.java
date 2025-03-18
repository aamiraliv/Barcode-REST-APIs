package com.microservice.order_service.controller;


import com.microservice.order_service.dto.OrderDTO;
import com.microservice.order_service.dto.OrderItemDTO;
import com.microservice.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping("/place/{userId}")
    public Map<String ,Object> placeOrder(@PathVariable Long userId, @RequestBody List<OrderItemDTO> items) {
        return service.placeOrder(userId, items);
    }

    @PutMapping("/{orderId}/update-status/{productId}")
    public void updateDeliveryStatus(@PathVariable Long orderId, @PathVariable Long productId, @RequestParam String status) {
        service.updateDeliveryStatus(orderId, productId, status);
    }


    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<OrderDTO> orderDTOS = service.GetALlOrders();
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrderById(@PathVariable Long orderId) {
        return service.getOrderById(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable Long userId) {
        return service.getOrdersByUserId(userId);
    }

//    @PutMapping("/{orderId}/update-payment")
//    public void updatePaymentStatus(@PathVariable Long orderId, @RequestParam String paymentStatus) {
//        service.updatePaymentStatus(orderId, paymentStatus);
//    }

}
