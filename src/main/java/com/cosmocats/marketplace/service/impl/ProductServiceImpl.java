package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.repository.CategoryRepository;
import com.cosmocats.marketplace.repository.ProductRepository;
import com.cosmocats.marketplace.repository.entity.CategoryEntity;
import com.cosmocats.marketplace.repository.entity.ProductEntity;
import com.cosmocats.marketplace.service.ProductService;
import com.cosmocats.marketplace.service.exception.CategoryNotFoundException;
import com.cosmocats.marketplace.service.exception.PersistenceException;
import com.cosmocats.marketplace.service.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productMapper.toProductList(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> {
            log.info("Product with id {} not found", productId);
            return new ProductNotFoundException(productId);
        });

        return productMapper.toProduct(product);
    }

    @Override
    @Transactional
    public Product createProduct(ProductDto productDto) {
        try {
            Product product = productMapper.toProduct(productRepository.save(productMapper.toProductEntity(productDto)));
            log.info("Product with id {} created", product.getId());
            return product;
        } catch (Exception ex) {
            log.error("Exception occurred while saving product");
            throw new PersistenceException(ex);
        }
    }

    @Override
    @Transactional
    public Product updateProductById(Long id, ProductDto productDto) {
        try {
            ProductEntity existingProduct = productRepository.findById(id).orElseThrow(() -> {
                log.warn("Product with id {} not found", id);
                return new ProductNotFoundException(id);
            });

            if (productDto.getCategoryId() != null) {
                CategoryEntity category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> {
                    log.warn("Category with id {} not found", productDto.getCategoryId());
                    return new CategoryNotFoundException(productDto.getCategoryId());
                });

                existingProduct.setCategory(category);
            }

            existingProduct.setName(productDto.getName());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setCurrency(productDto.getCurrency());
            existingProduct.setStock(productDto.getStock());

            ProductEntity saved = productRepository.save(existingProduct);

            log.info("Product with id {} successfully updated", id);

            return productMapper.toProduct(saved);
        }
        catch (Exception ex) {
            log.error("Exception occurred while updating product");
            throw new PersistenceException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
            log.info("Product with id {} deleted", id);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting product with id {}", id);
            throw new PersistenceException(ex);
        }
    }
}