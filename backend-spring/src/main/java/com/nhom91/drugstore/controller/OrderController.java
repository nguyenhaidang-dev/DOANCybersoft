package com.nhom91.drugstore.controller;

import com.nhom91.drugstore.dto.CreateOrderRequest;
import com.nhom91.drugstore.dto.OrderDTO;
import com.nhom91.drugstore.entity.User;
import com.nhom91.drugstore.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal User user) {
        OrderDTO order = orderService.createOrder(request, user.getId());
        return ResponseEntity.status(201).body(order);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders(@AuthenticationPrincipal User user) {
        List<OrderDTO> orders = orderService.getUserOrders(user.getId());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        OrderDTO order = orderService.getOrderById(id, user.getId());
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<OrderDTO> markOrderAsPaid(
            @PathVariable Long id,
            @RequestBody Map<String, Object> paymentData) {
        OrderDTO order = orderService.markOrderAsPaid(id, paymentData);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}/delivered")
    public ResponseEntity<OrderDTO> markOrderAsDelivered(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        OrderDTO order = orderService.markOrderAsDelivered(id, status);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<List<OrderDTO>> searchOrders(@PathVariable String search) {
        List<OrderDTO> orders = orderService.searchOrdersByUserEmail(search);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable String status) {
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/option/{option}")
    public ResponseEntity<List<OrderDTO>> getOrdersByTypePay(@PathVariable String option) {
        List<OrderDTO> orders = orderService.getOrdersByTypePay(option);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/combine/{status}/{option}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatusAndTypePay(
            @PathVariable String status,
            @PathVariable String option) {
        List<OrderDTO> orders = orderService.getOrdersByStatusAndTypePay(status, option);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/filter/{startDate}/{endDate}")
    public ResponseEntity<List<Map<String, Object>>> getOrderStatistics(
            @PathVariable String startDate,
            @PathVariable String endDate) {
        List<Map<String, Object>> stats = orderService.getOrderStatistics(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/prescription-order")
    public ResponseEntity<OrderDTO> createPrescriptionOrder(
            @RequestBody CreateOrderRequest request,
            @RequestParam String emailUser,
            @RequestParam String nameUser) {
        OrderDTO order = orderService.createPrescriptionOrder(request, emailUser, nameUser);
        return ResponseEntity.status(201).body(order);
    }

    @PostMapping("/order-repair")
    public ResponseEntity<OrderDTO> createOrderRepair(@RequestBody Map<String, Long> request) {
        Long orderId = request.get("id");
        OrderDTO order = orderService.createOrderRepair(orderId);
        return ResponseEntity.status(201).body(order);
    }

    // Migrated from NodeJS orderRoutes
}