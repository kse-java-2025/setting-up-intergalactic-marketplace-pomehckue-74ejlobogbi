package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.client.SupplierClient;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDto;
import com.cosmocats.marketplace.mapper.ProductMapper;
import com.cosmocats.marketplace.repository.ProductRepository;
import com.cosmocats.marketplace.repository.entity.ProductEntity;
import com.cosmocats.marketplace.service.ProductService;
import com.cosmocats.marketplace.service.exception.PersistenceException;
import com.cosmocats.marketplace.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SupplierClient supplierClient;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        log.info("Fetching all products from repository");
        List<ProductEntity> products = productRepository.findAll();
        return productMapper.toDomainList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> {
            log.info("Product with id {} not found", productId);
            return new ProductNotFoundException(productId);
        });

        return productMapper.toDomain(product);
    }

    @Override
    @Transactional
    public Product createProduct(ProductDto productDto) {
        log.info("Creating new product: {}", productDto.getName());

        if (!supplierClient.validatePrice(productDto.getName(), productDto.getPrice())) {
            log.warn("Price {} is below minimum for product {}",
                    productDto.getPrice(), productDto.getName());
        }

        try {
            Product product = productMapper.toDomain(productRepository.save(productMapper.toEntity(productDto)));
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
        log.info("Updating product with id: {}", id);

        try {
            ProductEntity existingProduct = productRepository.findById(id).orElseThrow(() -> {
                log.warn("Product with id {} not found", id);
                return new ProductNotFoundException(id);
            });

            productMapper.updateEntityFromDto(productDto, existingProduct);
            ProductEntity updatedProduct = productRepository.save(existingProduct);

            log.info("Product with id {} successfully updated", id);
            return productMapper.toDomain(updatedProduct);
        }
        catch (Exception ex) {
            log.error("Exception occurred while updating product");
            throw new PersistenceException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        log.info("Deleting product with id: {}", id);

        if (!productRepository.existsById(id)) {
            log.warn("Product with id {} not found for deletion", id);
            throw new ProductNotFoundException(id);
        }

        try {
            productRepository.deleteById(id);
            log.info("Product with id {} deleted", id);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting product with id {}", id);
            throw new PersistenceException(ex);
        }
    }
}
