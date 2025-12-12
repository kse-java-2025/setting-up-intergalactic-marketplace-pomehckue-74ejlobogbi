package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.client.SupplierClient;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDto;
import com.cosmocats.marketplace.mapper.ProductMapper;
import com.cosmocats.marketplace.repository.ProductRepository;
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
    private final SupplierClient supplierClient;

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products from repository");
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        log.info("Fetching product with id: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product with id {} not found", productId);
                    return new ProductNotFoundException(productId);
                });
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating new product: {}", productDto.getName());

        if (!supplierClient.validatePrice(productDto.getName(), productDto.getPrice())) {
            log.warn("Price {} is below minimum for product {}",
                    productDto.getPrice(), productDto.getName());
        }

        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProductById(UUID id, ProductDto productDto) {
        log.info("Updating product with id: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product with id {} not found for update", id);
                    return new ProductNotFoundException(id);
                });

        productMapper.updateEntityFromDto(productDto, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);

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
