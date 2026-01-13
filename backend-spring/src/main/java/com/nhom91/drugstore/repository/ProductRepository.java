package com.nhom91.drugstore.repository;

import com.nhom91.drugstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    // Migrated from Mongoose Product.find in NodeJS with text search
}