package com.cosmocats.marketplace.service.exception;

public class CategoryNotFoundException extends RuntimeException {
    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with id %s not found";

    public CategoryNotFoundException(Long categoryId) {
        super(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
    }
}
