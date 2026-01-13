package com.nhom91.drugstore.controller;

import com.nhom91.drugstore.dto.*;
import com.nhom91.drugstore.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/all/status")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesWithShow() {
        List<CategoryDTO> categories = categoryService.getAllCategoriesWithShow();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/all/status/no")
    public ResponseEntity<List<CategoryDTO>> getAllChildCategories() {
        List<CategoryDTO> categories = categoryService.getAllChildCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/all/status-detail/{id}")
    public ResponseEntity<List<CategoryDTO>> getChildCategoriesByParent(@PathVariable String id) {
        List<CategoryDTO> categories = categoryService.getChildCategoriesByParent(id);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryDTO category = categoryService.createCategory(request);
        return ResponseEntity.status(201).body(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(Map.of("message", "Category deleted"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest request) {
        CategoryDTO category = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<CategoryDTO> updateCategoryStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        Boolean isShow = body.get("isShow");
        CategoryDTO category = categoryService.updateCategoryStatus(id, isShow);
        return ResponseEntity.ok(category);
    }

    // Banner endpoints
    @GetMapping("/banner-detail/{detailId}")
    public ResponseEntity<BannerDTO> getBannerById(@PathVariable Long detailId) {
        BannerDTO banner = categoryService.getBannerById(detailId);
        return ResponseEntity.ok(banner);
    }

    @GetMapping("/all/banner")
    public ResponseEntity<List<BannerDTO>> getAllBanners() {
        List<BannerDTO> banners = categoryService.getAllBanners();
        return ResponseEntity.ok(banners);
    }

    @PostMapping("/banner")
    public ResponseEntity<BannerDTO> createBanner(@Valid @RequestBody CreateBannerRequest request) {
        BannerDTO banner = categoryService.createBanner(request);
        return ResponseEntity.status(200).body(banner);
    }

    @PutMapping("/banner/{id}")
    public ResponseEntity<BannerDTO> updateBanner(@PathVariable Long id, @RequestBody UpdateBannerRequest request) {
        BannerDTO banner = categoryService.updateBanner(id, request);
        return ResponseEntity.ok(banner);
    }

    @DeleteMapping("/banner/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteBanner(@PathVariable Long id) {
        categoryService.deleteBanner(id);
        return ResponseEntity.ok(Map.of("message", "Banner deleted"));
    }

    // Migrated from NodeJS categoryRoutes
}