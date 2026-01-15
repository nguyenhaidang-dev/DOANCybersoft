package com.datn.drugstore.repository;

import com.datn.drugstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByIdDesc(Long userId);

    List<Order> findAllByOrderByIdDesc();

    List<Order> findByStatus(String status);

    List<Order> findByTypePay(String typePay);

    List<Order> findByStatusAndTypePay(String status, String typePay);

    @Query("SELECT o FROM Order o WHERE o.status IS NULL")
    List<Order> findByStatusNull();

    @Query("SELECT o FROM Order o WHERE o.status IS NULL AND o.typePay = :typePay")
    List<Order> findByStatusNullAndTypePay(@Param("typePay") String typePay);

    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}