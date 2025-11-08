package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.service.exception.ProductNotFoundException;
import com.cosmocats.marketplace.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl();
    }

    @Test
    void getAllProducts_shouldReturnNonEmptyList() {
        List<Product> products = productService.getAllProducts();
        assertFalse(products.isEmpty());
    }

    @Test
    void getProductById_existingId_shouldReturnProduct() {
        Product existing = productService.getAllProducts().getFirst();
        Product product = productService.getProductById(existing.getId());
        assertEquals(existing.getId(), product.getId());
    }

    @Test
    void getProductById_nonExistingId_shouldThrow() {
        UUID randomId = UUID.randomUUID();
        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(randomId));
    }

    @Test
    void createProduct_shouldAddProduct() {
        int initialSize = productService.getAllProducts().size();
        ProductDto dto = new ProductDto();
        dto.name = "Star Toy";
        dto.description = "Cosmic toy for cats";
        dto.price = 10.0;
        dto.currency = "USD";
        dto.stock = 5;

        Product created = productService.createProduct(dto);

        assertNotNull(created.getId());
        assertEquals(initialSize + 1, productService.getAllProducts().size());
    }

    @Test
    void updateProductById_shouldUpdateFields() {
        Product existing = productService.getAllProducts().getFirst();
        ProductDto dto = new ProductDto();
        dto.name = "Updated Name";
        dto.description = "Updated description";
        dto.price = 99.99;
        dto.currency = "USD";
        dto.stock = 10;

        Product updated = productService.updateProductById(existing.getId(), dto);

        assertEquals("Updated Name", updated.getName());
        assertEquals("Updated description", updated.getDescription());
        assertEquals(99.99, updated.getPrice());
        assertEquals(10, updated.getStock());
    }

    @Test
    void deleteProductById_shouldRemoveProduct() {
        Product existing = productService.getAllProducts().getFirst();
        int initialSize = productService.getAllProducts().size();

        productService.deleteProductById(existing.getId());

        assertEquals(initialSize - 1, productService.getAllProducts().size());
        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(existing.getId()));
    }
}
