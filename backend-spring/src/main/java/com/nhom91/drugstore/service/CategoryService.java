package com.nhom91.drugstore.service;

import com.nhom91.drugstore.dto.*;
import com.nhom91.drugstore.request.CreateBannerRequest;
import com.nhom91.drugstore.request.CreateCategoryRequest;
import com.nhom91.drugstore.request.UpdateBannerRequest;
import com.nhom91.drugstore.request.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    // Category methods
    List<CategoryDTO> getAllCategories();
    List<CategoryDTO> getAllCategoriesWithShow();
    List<CategoryDTO> getAllChildCategories();
    List<CategoryDTO> getChildCategoriesByParent(String parentId);
    CategoryDTO getCategoryById(Long id);
    CategoryDTO createCategory(CreateCategoryRequest request);
    void deleteCategory(Long id);
    CategoryDTO updateCategory(Long id, UpdateCategoryRequest request);
    CategoryDTO updateCategoryStatus(Long id, Boolean isShow);
    
    // Banner methods
    BannerDTO getBannerById(Long id);
    List<BannerDTO> getAllBanners();
    BannerDTO createBanner(CreateBannerRequest request);
    BannerDTO updateBanner(Long id, UpdateBannerRequest request);
    void deleteBanner(Long id);
}