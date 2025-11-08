package com.cosmocats.marketplace.service.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Product with id %s not found";

    public ProductNotFoundException(UUID productId) {
        super(String.format(CUSTOMER_NOT_FOUND_MESSAGE, productId));
    }
}
