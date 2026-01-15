package com.nhom91.drugstore.service;

import com.nhom91.drugstore.dto.*;
import com.nhom91.drugstore.request.CreateOrderRequest;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderDTO createOrder(CreateOrderRequest request, Long userId);
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getUserOrders(Long userId);
    OrderDTO getOrderById(Long id, Long userId);
    OrderDTO markOrderAsPaid(Long id, Map<String, Object> paymentData);
    OrderDTO markOrderAsDelivered(Long id, String status);
    List<OrderDTO> searchOrdersByUserEmail(String email);
    List<OrderDTO> getOrdersByStatus(String status);
    List<OrderDTO> getOrdersByTypePay(String typePay);
    List<OrderDTO> getOrdersByStatusAndTypePay(String status, String typePay);
    List<Map<String, Object>> getOrderStatistics(String startDate, String endDate);
    OrderDTO createPrescriptionOrder(CreateOrderRequest request, String emailUser, String nameUser);
    OrderDTO createOrderRepair(Long orderId);
}