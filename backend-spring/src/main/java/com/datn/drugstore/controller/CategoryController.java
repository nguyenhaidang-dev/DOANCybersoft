package com.datn.drugstore.controller;

import com.datn.drugstore.dto.BannerDTO;
import com.datn.drugstore.dto.CategoryDTO;
import com.datn.drugstore.request.CreateBannerRequest;
import com.datn.drugstore.request.CreateCategoryRequest;
import com.datn.drugstore.request.UpdateBannerRequest;
import com.datn.drugstore.request.UpdateCategoryRequest;
import com.datn.drugstore.response.BaseResponse;
import com.datn.drugstore.service.CategoryService;
import com.datn.drugstore.utils.ResponseFactory;
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
    public ResponseEntity<BaseResponse> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseFactory.success(categories);
    }

    @GetMapping("/all/status")
    public ResponseEntity<BaseResponse> getAllCategoriesWithShow() {
        List<CategoryDTO> categories = categoryService.getAllCategoriesWithShow();
        return ResponseFactory.success(categories);
    }

    @GetMapping("/all/status/no")
    public ResponseEntity<BaseResponse> getAllChildCategories() {
        List<CategoryDTO> categories = categoryService.getAllChildCategories();
        return ResponseFactory.success(categories);
    }

    @GetMapping("/all/status-detail/{id}")
    public ResponseEntity<BaseResponse> getChildCategoriesByParent(@PathVariable String id) {
        List<CategoryDTO> categories = categoryService.getChildCategoriesByParent(id);
        return ResponseFactory.success(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseFactory.success(category);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryDTO category = categoryService.createCategory(request);
        return ResponseFactory.created(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseFactory.successMessage("Xóa danh mục thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest request) {
        CategoryDTO category = categoryService.updateCategory(id, request);
        return ResponseFactory.success(category, "Cập nhật danh mục thành công");
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<BaseResponse> updateCategoryStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        Boolean isShow = body.get("isShow");
        CategoryDTO category = categoryService.updateCategoryStatus(id, isShow);
        return ResponseFactory.success(category, "Cập nhật trạng thái thành công");
    }

    // Banner endpoints
    @GetMapping("/banner-detail/{detailId}")
    public ResponseEntity<BaseResponse> getBannerById(@PathVariable Long detailId) {
        BannerDTO banner = categoryService.getBannerById(detailId);
        return ResponseFactory.success(banner);
    }

    @GetMapping("/all/banner")
    public ResponseEntity<BaseResponse> getAllBanners() {
        List<BannerDTO> banners = categoryService.getAllBanners();
        return ResponseFactory.success(banners);
    }

    @PostMapping("/banner")
    public ResponseEntity<BaseResponse> createBanner(@Valid @RequestBody CreateBannerRequest request) {
        BannerDTO banner = categoryService.createBanner(request);
        return ResponseFactory.success(banner, "Tạo banner thành công");
    }

    @PutMapping("/banner/{id}")
    public ResponseEntity<BaseResponse> updateBanner(@PathVariable Long id, @RequestBody UpdateBannerRequest request) {
        BannerDTO banner = categoryService.updateBanner(id, request);
        return ResponseFactory.success(banner, "Cập nhật banner thành công");
    }

    @DeleteMapping("/banner/delete/{id}")
    public ResponseEntity<BaseResponse> deleteBanner(@PathVariable Long id) {
        categoryService.deleteBanner(id);
        return ResponseFactory.successMessage("Xóa banner thành công");
    }
}
