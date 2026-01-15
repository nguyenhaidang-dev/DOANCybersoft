package com.nhom91.drugstore.service;

import com.nhom91.drugstore.dto.*;
import com.nhom91.drugstore.entity.User;
import com.nhom91.drugstore.request.CreateProductRequest;
import com.nhom91.drugstore.request.ProductReviewRequest;
import com.nhom91.drugstore.request.UpdateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> getAllProducts(String keyword, int page, int size);
    List<ProductDTO> getAllProductsAdmin();
    List<ProductDTO> getAllProductsPrescription();
    ProductDTO getProductById(Long id);
    ProductDTO createProduct(CreateProductRequest request, Long userId);
    ProductDTO updateProduct(Long id, UpdateProductRequest request);
    void deleteProduct(Long id);
    void addProductReview(Long productId, ProductReviewRequest request, User user);
    List<ProductDTO> searchProducts(String type);
    List<ProductDTO> searchPrescriptionProducts(String type);
    List<ProductDTO> getProductsByCategory(Long categoryId);
}