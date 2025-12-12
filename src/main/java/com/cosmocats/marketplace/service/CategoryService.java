package com.cosmocats.marketplace.service;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category createCategory(CategoryDto order);

    void deleteCategoryById(Long id);
}
