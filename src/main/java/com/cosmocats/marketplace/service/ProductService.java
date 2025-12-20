package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    
    List<ProductDto> getAllProducts();

    ProductDto getProductById(UUID id);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProductById(UUID id, ProductDto productDto);

    void deleteProductById(UUID id);
}
