package com.nhom91.drugstore.service.impl;
import com.nhom91.drugstore.dto.*;
import com.nhom91.drugstore.entity.*;
import com.nhom91.drugstore.repository.OrderRepository;
import com.nhom91.drugstore.repository.ProductRepository;
import com.nhom91.drugstore.repository.UserRepository;
import com.nhom91.drugstore.request.CreateOrderRequest;
import com.nhom91.drugstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setTaxPrice(request.getTaxPrice() != null ? request.getTaxPrice() : BigDecimal.ZERO);
        order.setShippingPrice(request.getShippingPrice() != null ? request.getShippingPrice() : BigDecimal.ZERO);
        order.setTotalPrice(request.getTotalPrice() != null ? request.getTotalPrice() : BigDecimal.ZERO);
        order.setTypePay(request.getTypePay());
        order.setIsReceive(false);

        // Create shipping address
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(request.getShippingAddress().getAddress());
        shippingAddress.setCity(request.getShippingAddress().getCity());
        shippingAddress.setPostalCode(request.getShippingAddress().getPostalCode());
        shippingAddress.setCountry(request.getShippingAddress().getCountry());
        shippingAddress.setOrder(order);
        order.setShippingAddress(shippingAddress);

        // Create order items
        List<OrderItem> orderItems = request.getOrderItems().stream().map(itemRequest -> {
            Product product = productRepository.findById(itemRequest.getProduct())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setName(itemRequest.getName());
            orderItem.setQty(itemRequest.getQty());
            orderItem.setImage(itemRequest.getImage());
            orderItem.setPrice(itemRequest.getPrice());
            orderItem.setLoanPrice(itemRequest.getLoanPrice());
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAllByOrderByIdDesc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByIdDesc(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id, Long userId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Check if user owns the order or is admin
        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to view this order");
        }

        return convertToDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO markOrderAsPaid(Long id, Map<String, Object> paymentData) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setIsPaid(true);
        order.setPaidAt(LocalDateTime.now());

        if (paymentData != null && !paymentData.isEmpty()) {
            try {
                PaymentResult paymentResult = new PaymentResult();
                String paymentId = null;
                if (paymentData.get("id") != null) {
                    paymentId = paymentData.get("id").toString();
                } else if (paymentData.get("paymentID") != null) {
                    paymentId = paymentData.get("paymentID").toString();
                } else if (paymentData.get("paymentId") != null) {
                    paymentId = paymentData.get("paymentId").toString();
                }
                paymentResult.setPaymentId(paymentId);
                
                String status = null;
                if (paymentData.get("status") != null) {
                    status = paymentData.get("status").toString();
                } else if (paymentData.get("state") != null) {
                    status = paymentData.get("state").toString();
                } else {
                    status = "COMPLETED";
                }
                paymentResult.setStatus(status);
                
                String updateTime = null;
                if (paymentData.get("update_time") != null) {
                    updateTime = paymentData.get("update_time").toString();
                } else if (paymentData.get("updateTime") != null) {
                    updateTime = paymentData.get("updateTime").toString();
                } else {
                    updateTime = LocalDateTime.now().toString();
                }
                paymentResult.setUpdateTime(updateTime);
                
                String emailAddress = null;
                if (paymentData.get("email_address") != null) {
                    emailAddress = paymentData.get("email_address").toString();
                } else if (paymentData.get("payer") != null) {
                    Object payerObj = paymentData.get("payer");
                    if (payerObj instanceof Map) {
                        Map<?, ?> payer = (Map<?, ?>) payerObj;
                        if (payer.get("email_address") != null) {
                            emailAddress = payer.get("email_address").toString();
                        }
                    }
                }
                paymentResult.setEmailAddress(emailAddress);
                
                paymentResult.setOrder(order);
                order.setPaymentResult(paymentResult);
            } catch (Exception e) {
                // Continue without PaymentResult - order can still be marked as paid
            }
        }

        try {
            Order updatedOrder = orderRepository.save(order);
            orderRepository.flush();
            return convertToDTO(updatedOrder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save order payment status: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public OrderDTO markOrderAsDelivered(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setIsDelivered(true);
        order.setDeliveredAt(LocalDateTime.now());
        order.setStatus(status);

        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    @Override
    public List<OrderDTO> searchOrdersByUserEmail(String email) {
        List<User> users = userRepository.findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .collect(Collectors.toList());

        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());

        return orderRepository.findAll().stream()
                .filter(order -> userIds.contains(order.getUser().getId()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(String status) {
        if ("choxuli".equals(status)) {
            return orderRepository.findByStatusNull().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if ("default".equals(status)) {
            return orderRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return orderRepository.findByStatus(status).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<OrderDTO> getOrdersByTypePay(String typePay) {
        if ("default".equals(typePay)) {
            return orderRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return orderRepository.findByTypePay(typePay).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<OrderDTO> getOrdersByStatusAndTypePay(String status, String typePay) {
        if ("choxuli".equals(status)) {
            return orderRepository.findByStatusNullAndTypePay(typePay).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return orderRepository.findByStatusAndTypePay(status, typePay).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Map<String, Object>> getOrderStatistics(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");

        List<Order> orders = orderRepository.findByCreatedAtBetween(start, end);

        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> Map.of(
                                "month", order.getCreatedAt().getMonthValue(),
                                "year", order.getCreatedAt().getYear()
                        ),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("_id", list.get(0).getCreatedAt().getMonthValue() + "/" + list.get(0).getCreatedAt().getYear());
                                    map.put("count", (long) list.size());
                                    map.put("totalPrice", list.stream().mapToDouble(o -> o.getTotalPrice().doubleValue()).sum());
                                    return map;
                                }
                        )
                ))
                .values()
                .stream()
                .sorted((a, b) -> Integer.compare((Integer) a.get("_id"), (Integer) b.get("_id")))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO createPrescriptionOrder(CreateOrderRequest request, String emailUser, String nameUser) {
        User user = userRepository.findByEmail(emailUser).orElse(null);
        if (user == null) {
            // Create new user
            user = new User();
            user.setName(nameUser);
            user.setEmail(emailUser);
            user.setPassword("$2b$10$VUS8veWKAvr9Si80fJy3Ye3ocGdAWfdTWKIGzWiT2QEqAbrh3xt22"); // default password
            user = userRepository.save(user);
        }

        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setTaxPrice(request.getTaxPrice());
        order.setShippingPrice(request.getShippingPrice());
        order.setTotalPrice(request.getTotalPrice());
        order.setTypePay(request.getTypePay());
        order.setStatus(request.getStatus());
        order.setIsDelivered(request.getIsDelivered());
        order.setIsPaid(request.getIsPaid());
        order.setDeliveredAt(request.getDeliveredAt() != null ? LocalDateTime.parse(request.getDeliveredAt()) : null);

        // Create shipping address
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(request.getShippingAddress().getAddress());
        shippingAddress.setCity(request.getShippingAddress().getCity());
        shippingAddress.setPostalCode(request.getShippingAddress().getPostalCode());
        shippingAddress.setCountry(request.getShippingAddress().getCountry());
        shippingAddress.setOrder(order);
        order.setShippingAddress(shippingAddress);

        // Create payment result if provided
        if (request.getPaymentResult() != null) {
            PaymentResult paymentResult = new PaymentResult();
            paymentResult.setPaymentId(request.getPaymentResult().getPaymentId());
            paymentResult.setStatus(request.getPaymentResult().getStatus());
            paymentResult.setUpdateTime(request.getPaymentResult().getUpdateTime());
            paymentResult.setEmailAddress(request.getPaymentResult().getEmailAddress());
            paymentResult.setOrder(order);
            order.setPaymentResult(paymentResult);
        }

        // Create order items
        List<OrderItem> orderItems = request.getOrderItems().stream().map(itemRequest -> {
            Product product = productRepository.findById(itemRequest.getProduct())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setName(itemRequest.getName());
            orderItem.setQty(itemRequest.getQty());
            orderItem.setImage(itemRequest.getImage());
            orderItem.setPrice(itemRequest.getPrice());
            orderItem.setLoanPrice(itemRequest.getLoanPrice());
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO createOrderRepair(Long orderId) {
        Order originalOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Order repairOrder = new Order();
        repairOrder.setUser(originalOrder.getUser());
        repairOrder.setOrderItems(originalOrder.getOrderItems().stream().map(item -> {
            OrderItem newItem = new OrderItem();
            newItem.setName(item.getName());
            newItem.setQty(item.getQty());
            newItem.setImage(item.getImage());
            newItem.setPrice(item.getPrice());
            newItem.setLoanPrice(item.getLoanPrice());
            newItem.setProduct(item.getProduct());
            newItem.setOrder(repairOrder);
            return newItem;
        }).collect(Collectors.toList()));

        ShippingAddress repairAddress = new ShippingAddress();
        repairAddress.setAddress("Äáº·t táº¡i quĂ¡n");
        repairAddress.setCity("Sáº£n pháº©m Ä‘Æ°á»£c mua táº¡i chá»—");
        repairAddress.setPostalCode("QCODE-200");
        repairAddress.setCountry("VN");
        repairAddress.setOrder(repairOrder);
        repairOrder.setShippingAddress(repairAddress);

        repairOrder.setPaymentMethod("Paypal");
        repairOrder.setTaxPrice(BigDecimal.ZERO);
        repairOrder.setShippingPrice(BigDecimal.ZERO);
        repairOrder.setTotalPrice(originalOrder.getTotalPrice());
        repairOrder.setTypePay("buy");
        repairOrder.setIsPaid(false);
        repairOrder.setIsDelivered(false);

        Order savedOrder = orderRepository.save(repairOrder);
        return convertToDTO(savedOrder);
    }

    private OrderDTO convertToDTO(Order order) {
        UserDTO userDTO = new UserDTO(order.getUser().getId(), order.getUser().getName(),
                order.getUser().getEmail(), order.getUser().getPhone(), order.getUser().getIsAdmin(), order.getUser().getCreatedAt(), null);

        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                .map(item -> new OrderItemDTO(item.getId(), item.getName(), item.getQty(), item.getImage(),
                        item.getPrice(), item.getLoanPrice(),
                        new ProductDTO(item.getProduct().getId(), item.getProduct().getMa(), item.getProduct().getName(),
                                item.getProduct().getImage(), item.getProduct().getDescription(), null,
                                item.getProduct().getRating(), item.getProduct().getNumReviews(), null,
                                item.getProduct().getPrice(), item.getProduct().getCountInStock(),
                                item.getProduct().getLoanPrice(), item.getProduct().getIsBought(),
                                item.getProduct().getCreatedAt(), item.getProduct().getUpdatedAt())))
                .collect(Collectors.toList());

        ShippingAddressDTO shippingAddressDTO = order.getShippingAddress() != null ?
                new ShippingAddressDTO(order.getShippingAddress().getId(), order.getShippingAddress().getAddress(),
                        order.getShippingAddress().getCity(), order.getShippingAddress().getPostalCode(),
                        order.getShippingAddress().getCountry()) : null;

        PaymentResultDTO paymentResultDTO = order.getPaymentResult() != null ?
                new PaymentResultDTO(order.getPaymentResult().getId(), order.getPaymentResult().getPaymentId(),
                        order.getPaymentResult().getStatus(), order.getPaymentResult().getUpdateTime(),
                        order.getPaymentResult().getEmailAddress()) : null;

        return new OrderDTO(order.getId(), userDTO, order.getPaymentMethod(), order.getTaxPrice(),
                order.getShippingPrice(), order.getTotalPrice(), order.getTypePay(), order.getIsPaid(),
                order.getPaidAt(), order.getIsDelivered(), order.getDeliveredAt(), order.getStatus(),
                order.getIsReceive(), orderItemDTOs, shippingAddressDTO, paymentResultDTO,
                order.getCreatedAt(), order.getUpdatedAt());
    }

    // Migrated from NodeJS orderRoutes logic
}
