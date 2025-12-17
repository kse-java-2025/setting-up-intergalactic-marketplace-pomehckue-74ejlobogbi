package com.cosmocats.marketplace.web;

import com.cosmocats.marketplace.dto.CategoryDto;
import com.cosmocats.marketplace.service.CategoryService;
import com.cosmocats.marketplace.mapper.CategoryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryMapper.toDtoList(categoryService.getAllCategories()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.getCategoryById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('COSMO_ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto){
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.createCategory(categoryDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COSMO_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
