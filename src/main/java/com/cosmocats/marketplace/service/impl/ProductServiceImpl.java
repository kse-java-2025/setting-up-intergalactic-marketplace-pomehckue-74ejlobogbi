package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.client.SupplierClient;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDto;
import com.cosmocats.marketplace.mapper.ProductMapper;
import com.cosmocats.marketplace.persistence.entity.ProductEntity;
import com.cosmocats.marketplace.persistence.mapper.ProductPersistenceMapper;
import com.cosmocats.marketplace.persistence.repository.ProductRepository;
import com.cosmocats.marketplace.service.ProductService;
import com.cosmocats.marketplace.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductPersistenceMapper persistenceMapper;
    private final SupplierClient supplierClient;

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products from repository");
        List<ProductEntity> entities = productRepository.findAll();
        List<Product> products = persistenceMapper.toDomainList(entities);
        return productMapper.toDtoList(products);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        log.info("Fetching product with id: {}", productId);
        ProductEntity entity = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product with id {} not found", productId);
                    return new ProductNotFoundException(productId);
                });
        Product product = persistenceMapper.toDomain(entity);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating new product: {}", productDto.name());

        if (!supplierClient.validatePrice(productDto.name(), productDto.price())) {
            log.warn("Price {} is below minimum for product {}",
                    productDto.price(), productDto.name());
        }

        Product product = productMapper.toDomain(productDto);
        ProductEntity entity = persistenceMapper.toEntity(product);
        ProductEntity savedEntity = productRepository.save(entity);
        Product savedProduct = persistenceMapper.toDomain(savedEntity);

        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProductById(UUID id, ProductDto productDto) {
        log.info("Updating product with id: {}", id);

        ProductEntity existingEntity = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product with id {} not found for update", id);
                    return new ProductNotFoundException(id);
                });

        Product existingProduct = persistenceMapper.toDomain(existingEntity);
        productMapper.updateDomainFromDto(productDto, existingProduct);

        ProductEntity updatedEntity = persistenceMapper.toEntity(existingProduct);
        updatedEntity.setId(id);
        ProductEntity savedEntity = productRepository.save(updatedEntity);
        Product updatedProduct = persistenceMapper.toDomain(savedEntity);

        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProductById(UUID id) {
        log.info("Deleting product with id: {}", id);

        if (!productRepository.existsById(id)) {
            log.warn("Product with id {} not found for deletion", id);
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }
}
