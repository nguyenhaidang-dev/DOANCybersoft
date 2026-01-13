package com.nhom91.drugstore.controller;

import com.nhom91.drugstore.dto.*;
import com.nhom91.drugstore.entity.User;
import com.nhom91.drugstore.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int pageNumber) {
        Page<ProductDTO> page = productService.getAllProducts(keyword, pageNumber, 12);
        Map<String, Object> response = Map.of(
                "products", page.getContent(),
                "page", page.getNumber() + 1,
                "pages", page.getTotalPages()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAllProductsAdmin() {
        List<ProductDTO> products = productService.getAllProductsAdmin();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/all-prescription")
    public ResponseEntity<List<ProductDTO>> getAllProductsPrescription() {
        List<ProductDTO> products = productService.getAllProductsPrescription();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/{type}")
    public ResponseEntity<List<ProductDTO>> searchProducts(@PathVariable String type) {
        List<ProductDTO> products = productService.searchProducts(type);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search-prescription/{type}")
    public ResponseEntity<List<ProductDTO>> searchPrescriptionProducts(@PathVariable String type) {
        List<ProductDTO> products = productService.searchPrescriptionProducts(type);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Map<String, String>> addProductReview(
            @PathVariable Long id,
            @Valid @RequestBody ProductReviewRequest request,
            @AuthenticationPrincipal User user) {
        productService.addProductReview(id, request, user);
        return ResponseEntity.status(201).body(Map.of("message", "Review added"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(Map.of("message", "Product deleted"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody CreateProductRequest request,
            @AuthenticationPrincipal User user) {
        ProductDTO product = productService.createProduct(request, user.getId());
        return ResponseEntity.status(201).body(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request) {
        ProductDTO product = productService.updateProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/searchProduct/{option}")
    public ResponseEntity<List<ProductDTO>> searchProductByOption(@PathVariable String option) {
        List<ProductDTO> products;
        if ("old".equals(option)) {
            products = productService.getAllProductsAdmin();
        } else {
            products = productService.getAllProductsAdmin();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/searchHere/{q}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long q) {
        List<ProductDTO> products = productService.getProductsByCategory(q);
        return ResponseEntity.ok(products);
    }

    // Migrated from NodeJS ProductRoutes
}