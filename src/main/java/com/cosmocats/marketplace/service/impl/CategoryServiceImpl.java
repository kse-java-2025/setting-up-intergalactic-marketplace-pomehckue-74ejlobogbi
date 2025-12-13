package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.dto.CategoryDto;
import com.cosmocats.marketplace.mapper.CategoryMapper;
import com.cosmocats.marketplace.repository.CategoryRepository;
import com.cosmocats.marketplace.repository.entity.CategoryEntity;
import com.cosmocats.marketplace.service.CategoryService;
import com.cosmocats.marketplace.service.exception.CategoryNotFoundException;
import com.cosmocats.marketplace.service.exception.PersistenceException;
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
        log.info("Creating new category: {}", categoryDto.getName());

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
    public Category updateCategory(Long id, CategoryDto categoryDto) {
        log.info("Updating category with id: {}", id);

        CategoryEntity existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        try {
            categoryMapper.updateEntityFromDto(categoryDto, existingCategory);
            CategoryEntity saved = categoryRepository.save(existingCategory);
            return categoryMapper.toDomain(saved);
        } catch (Exception ex) {
            log.error("Exception occurred while updating category");
            throw new PersistenceException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        log.info("Deleting category with id: {}", id);

        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }

        try {
            categoryRepository.deleteById(id);
            log.info("Category with id {} deleted", id);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting category with id {}", id);
            throw new PersistenceException(ex);
        }
    }
}