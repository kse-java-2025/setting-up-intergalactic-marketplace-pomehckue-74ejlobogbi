package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category createCategory(CategoryDto order);

    Category updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategoryById(Long id);
}
