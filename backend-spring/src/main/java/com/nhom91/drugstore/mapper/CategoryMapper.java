package com.nhom91.drugstore.mapper;

import com.nhom91.drugstore.dto.CategoryDTO;
import com.nhom91.drugstore.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setIsShow(category.getIsShow());
        dto.setParentCategory(category.getParentCategory());
        dto.setIsParent(category.getIsParent());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        
        return dto;
    }

    public static List<CategoryDTO> toDTOList(List<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
