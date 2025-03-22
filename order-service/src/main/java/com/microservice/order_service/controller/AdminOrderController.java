package com.microservice.order_service.controller;

import com.microservice.order_service.dto.OrderDTO;
import com.microservice.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService service;

    @PutMapping("/{orderId}/update-status/{productId}")
    public void updateDeliveryStatus(@PathVariable Long orderId, @PathVariable Long productId, @RequestParam String status) {
        service.updateDeliveryStatus(orderId, productId, status);
    }


    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<OrderDTO> orderDTOS = service.GetALlOrders();
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }
}
