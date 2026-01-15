package com.datn.drugstore.service.impl;

import com.datn.drugstore.dto.BannerDTO;
import com.datn.drugstore.dto.CategoryDTO;
import com.datn.drugstore.request.CreateBannerRequest;
import com.datn.drugstore.request.CreateCategoryRequest;
import com.datn.drugstore.request.UpdateBannerRequest;
import com.datn.drugstore.request.UpdateCategoryRequest;
import com.datn.drugstore.entity.Banner;
import com.datn.drugstore.entity.Category;
import com.datn.drugstore.repository.BannerRepository;
import com.datn.drugstore.repository.CategoryRepository;
import com.datn.drugstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllCategoriesWithShow() {
        return categoryRepository.findByIsShowTrueAndIsParentTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllChildCategories() {
        return categoryRepository.findByIsParentFalse().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getChildCategoriesByParent(String parentId) {
        return categoryRepository.findByIsShowTrueAndIsParentFalseAndParentCategory(parentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        // Check if category name exists
        List<Category> existing = categoryRepository.findAll().stream()
                .filter(c -> c.getName().equals(request.getName()))
                .collect(Collectors.toList());
        if (!existing.isEmpty()) {
            throw new RuntimeException("Category name already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setIsShow(request.getIsShow() != null ? request.getIsShow() : false);
        category.setParentCategory(request.getParentCategory());
        category.setIsParent(request.getParentCategory() == null || request.getParentCategory().isEmpty());

        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (request.getName() != null) category.setName(request.getName());
        if (request.getDescription() != null) category.setDescription(request.getDescription());
        if (request.getIsShow() != null) category.setIsShow(request.getIsShow());
        if (request.getParentCategory() != null) {
            category.setParentCategory(request.getParentCategory());
            category.setIsParent(request.getParentCategory() == null || request.getParentCategory().isEmpty());
        }

        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategoryStatus(Long id, Boolean isShow) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setIsShow(isShow);
        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    // Banner methods
    @Override
    public BannerDTO getBannerById(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
        return convertBannerToDTO(banner);
    }

    @Override
    public List<BannerDTO> getAllBanners() {
        return bannerRepository.findAll().stream()
                .map(this::convertBannerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BannerDTO createBanner(CreateBannerRequest request) {
        // Check if banner linkImg exists
        List<Banner> existing = bannerRepository.findAll().stream()
                .filter(b -> b.getLinkImg().equals(request.getLinkImg()))
                .collect(Collectors.toList());
        if (!existing.isEmpty()) {
            throw new RuntimeException("Banner link image already exists");
        }

        Banner banner = new Banner();
        banner.setLinkImg(request.getLinkImg());
        banner.setLinkPage(request.getLinkPage());
        banner.setIsShow(request.getIsShow() != null ? request.getIsShow() : false);

        Banner savedBanner = bannerRepository.save(banner);
        return convertBannerToDTO(savedBanner);
    }

    @Override
    @Transactional
    public BannerDTO updateBanner(Long id, UpdateBannerRequest request) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));

        if (request.getLinkImg() != null) banner.setLinkImg(request.getLinkImg());
        if (request.getLinkPage() != null) banner.setLinkPage(request.getLinkPage());
        if (request.getIsShow() != null) banner.setIsShow(request.getIsShow());

        Banner updatedBanner = bannerRepository.save(banner);
        return convertBannerToDTO(updatedBanner);
    }

    @Override
    @Transactional
    public void deleteBanner(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
        bannerRepository.delete(banner);
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription(),
                category.getIsShow(), category.getParentCategory(), category.getIsParent(),
                category.getCreatedAt(), category.getUpdatedAt());
    }

    private BannerDTO convertBannerToDTO(Banner banner) {
        return new BannerDTO(banner.getId(), banner.getLinkImg(), banner.getLinkPage(),
                banner.getIsShow(), banner.getCreatedAt(), banner.getUpdatedAt());
    }
}
