package com.microservice.order_service.service;

import com.microservice.order_service.dto.OrderDTO;
import com.microservice.order_service.dto.OrderItemDTO;
import com.microservice.order_service.dto.ProductResponse;
import com.microservice.order_service.dto.ProductSalesUpdateRequest;
import com.microservice.order_service.feign.ProductServiceClient;
import com.microservice.order_service.model.Order;
import com.microservice.order_service.model.OrderItem;
import com.microservice.order_service.repository.OrderItemRepository;
import com.microservice.order_service.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductServiceClient productServiceClient;


    public List<OrderDTO> GetALlOrders() {
        return repository.findAll()
                .stream()
                .filter(item-> !item.getOrderItems().isEmpty())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Map<String, Object> placeOrder(Long userId, List<OrderItemDTO> items) {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatus("Processing");

        List<OrderItem> orderItems = items.stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setDeliveryStatus("Pending");
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        repository.save(order);

        updateProductSales(items);
        productServiceClient.clearCart(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("order", convertToDTO(order));

        return response;
    }

    public void updateProductSales(List<OrderItemDTO> items) {
        List<ProductSalesUpdateRequest> salesUpdateRequests = items.stream().map(item -> new ProductSalesUpdateRequest(item.getProductId(), item.getQuantity())).collect(Collectors.toList());

        productServiceClient.updateProductSales(salesUpdateRequests);
    }

    public OrderDTO getOrderById(Long orderId) {
        return repository.findById(orderId).map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return repository.findByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void updateDeliveryStatus(Long orderId, Long productId, String newStatus) {
        Order order = repository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        order.getOrderItems().forEach(item -> {
            if (item.getProductId().equals(productId)) {
                item.setDeliveryStatus(newStatus);
            }
        });

        repository.save(order);
    }


    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(order.getId(), order.getUserId(), order.getOrderStatus(), order.getOrderItems().stream().map(item -> {
            ProductResponse product = null;
            try {
                product = productServiceClient.getProductById(item.getProductId());
            } catch (Exception e) {
                System.out.println("Failed to fetch product details for ID " + item.getProductId() + ": " + e.getMessage());
            }
            return new OrderItemDTO(item.getProductId(), item.getQuantity(), item.getDeliveryStatus(), product);
        }).collect(Collectors.toList()));
    }


    public OrderDTO cancelOrder(Long orderId, Long productId) {
        Order order = repository.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));

        OrderItem orderItemToDelete = order.getOrderItems().stream().filter(item -> item.getProductId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("product not found"));
        order.getOrderItems().remove(orderItemToDelete);
        orderItemRepository.delete(orderItemToDelete);

        repository.save(order);
        return convertToDTO(order);
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = repository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(status);
        repository.save(order);
    }
}
