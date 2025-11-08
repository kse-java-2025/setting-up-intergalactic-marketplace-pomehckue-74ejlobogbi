package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.service.ProductService;
import com.cosmocats.marketplace.service.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final List<Product> products = new ArrayList<>(buildAllProductsMock());

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(UUID productId) {
        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> {
                    log.info("Product with id {} not found in mock", productId);
                    return new ProductNotFoundException(productId);
                });
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(productDto.name);
        product.setDescription(productDto.description);
        product.setPrice(productDto.price);
        product.setCurrency(productDto.currency);
        product.setStock(productDto.stock);

        products.add(product);
        log.info("Product with id {} created", product.getId());

        return product;
    }

    @Override
    public Product updateProductById(UUID id, ProductDto productDto) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(productDto.name);
        existingProduct.setDescription(productDto.description);
        existingProduct.setPrice(productDto.price);
        existingProduct.setCurrency(productDto.currency);
        existingProduct.setStock(productDto.stock);

        log.info("Product with id {} updated", id);

        return existingProduct;
    }

    @Override
    public void deleteProductById(UUID id) {
        products.removeIf(details -> details.getId().equals(id));
        log.info("Product with id {} deleted", id);
    }

    private List<Product> buildAllProductsMock() {
        return List.of(
                Product.builder()
                        .id(UUID.randomUUID())
                        .name("Space yarn balls")
                        .description("Anti-gravity yarn balls")
                        .price(6)
                        .currency("USD")
                        .stock(7)
                        .build(),
                Product.builder()
                        .id(UUID.randomUUID())
                        .name("Cosmic milk")
                        .description("Super tasty cosmic milk")
                        .price(2)
                        .currency("USD")
                        .stock(12)
                        .build(),
                Product.builder()
                        .id(UUID.randomUUID())
                        .name("Comet cheese")
                        .description("Cheese made of comets")
                        .price(14)
                        .currency("USD")
                        .stock(2)
                        .build());
    }
}