package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.dto.CategoryDto;
import com.cosmocats.marketplace.mapper.CategoryMapper;
import com.cosmocats.marketplace.repository.CategoryRepository;
import com.cosmocats.marketplace.repository.entity.CategoryEntity;
import com.cosmocats.marketplace.service.CategoryService;
import com.cosmocats.marketplace.service.exception.CategoryNotFoundException;
import com.cosmocats.marketplace.service.exception.PersistenceException;
import com.cosmocats.marketplace.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryMapper.toDomainList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.info("Category with id {} not found", categoryId);
            return new CategoryNotFoundException(categoryId);
        });

        return categoryMapper.toDomain(category);
    }

    @Override
    @Transactional
    public Category createCategory(CategoryDto categoryDto) {
        try {
            CategoryEntity entity = categoryMapper.toEntity(categoryDto);
            entity.setCategoryReference(UUID.randomUUID());
            CategoryEntity saved = categoryRepository.save(entity);

            log.info("Category with id {} created", saved.getId());
            return categoryMapper.toDomain(saved);
        } catch (Exception ex) {
            log.error("Exception occurred while saving category");
            throw new PersistenceException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        try {
            categoryRepository.deleteById(id);
            log.info("Category with id {} deleted", id);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting category with id {}", id);
            throw new PersistenceException(ex);
        }
    }
}