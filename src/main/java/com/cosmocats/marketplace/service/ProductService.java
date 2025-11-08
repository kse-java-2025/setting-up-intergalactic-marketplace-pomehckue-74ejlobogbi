package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(UUID id);

    Product createProduct(ProductDto productDto);

    Product updateProductById(UUID id, ProductDto productDto);

    void deleteProductById(UUID id);
}