package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.client.SupplierClient;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDto;
import com.cosmocats.marketplace.mapper.ProductMapper;
import com.cosmocats.marketplace.persistence.entity.ProductEntity;
import com.cosmocats.marketplace.persistence.mapper.ProductPersistenceMapper;
import com.cosmocats.marketplace.persistence.repository.ProductRepository;
import com.cosmocats.marketplace.service.exception.ProductNotFoundException;
import com.cosmocats.marketplace.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductPersistenceMapper persistenceMapper;

    @Mock
    private SupplierClient supplierClient;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductEntity productEntity;
    private Product product;
    private ProductDto productDto;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();

        productEntity = ProductEntity.builder()
                .id(productId)
                .name("Cosmic Milk")
                .description("Fresh from Milky Way")
                .price(10.0)
                .stock(50)
                .build();

        product = Product.builder()
                .id(productId)
                .name("Cosmic Milk")
                .description("Fresh from Milky Way")
                .price(10.0)
                .stock(50)
                .build();

        productDto = ProductDto.builder()
                .id(productId)
                .name("Cosmic Milk")
                .description("Fresh from Milky Way")
                .price(10.0)
                .stock(50)
                .build();
    }

    @Test
    void getAllProducts_shouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(productEntity));
        when(persistenceMapper.toDomainList(any())).thenReturn(List.of(product));
        when(productMapper.toDtoList(any())).thenReturn(List.of(productDto));

        List<ProductDto> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(productId, result.getFirst().id());
        assertEquals("Cosmic Milk", result.getFirst().name());
        verify(productRepository).findAll();
        verify(persistenceMapper).toDomainList(any());
        verify(productMapper).toDtoList(any());
    }

    @Test
    void getProductById_found_shouldReturnProduct() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(persistenceMapper.toDomain(productEntity)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);

        ProductDto result = productService.getProductById(productId);

        assertEquals(productId, result.id());
        assertEquals("Cosmic Milk", result.name());
        assertEquals(10.0, result.price());
        verify(productRepository).findById(productId);
        verify(persistenceMapper).toDomain(productEntity);
        verify(productMapper).toDto(product);
    }

    @Test
    void getProductById_notFound_shouldThrow() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(productId));

        verify(productRepository).findById(productId);
        verifyNoInteractions(persistenceMapper);
        verifyNoInteractions(productMapper);
    }

    @Test
    void createProduct_shouldSave() {
        when(productMapper.toDomain(productDto)).thenReturn(product);
        when(persistenceMapper.toEntity(product)).thenReturn(productEntity);
        when(productRepository.save(any())).thenReturn(productEntity);
        when(persistenceMapper.toDomain(productEntity)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);
        when(supplierClient.validatePrice(anyString(), anyDouble())).thenReturn(true);

        ProductDto result = productService.createProduct(productDto);

        assertNotNull(result);
        assertEquals(productId, result.id());
        assertEquals("Cosmic Milk", result.name());
        verify(productRepository).save(any());
        verify(supplierClient).validatePrice("Cosmic Milk", 10.0);
    }

    @Test
    void updateProductById_found_shouldUpdate() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(persistenceMapper.toDomain(productEntity)).thenReturn(product);
        when(persistenceMapper.toEntity(product)).thenReturn(productEntity);
        when(productRepository.save(any())).thenReturn(productEntity);
        when(persistenceMapper.toDomain(productEntity)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);

        ProductDto result = productService.updateProductById(productId, productDto);

        assertNotNull(result);
        assertEquals(productId, result.id());
        verify(productMapper).updateDomainFromDto(productDto, product);
        verify(productRepository).save(any());
    }

    @Test
    void updateProductById_notFound_shouldThrow() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProductById(productId, productDto));

        verify(productRepository).findById(productId);
        verifyNoInteractions(persistenceMapper);
        verify(productMapper, never()).updateDomainFromDto(any(), any());
    }

    @Test
    void deleteProductById_found_shouldDelete() {
        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProductById(productId);

        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProductById_notFound_shouldThrow() {
        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProductById(productId));

        verify(productRepository).existsById(productId);
        verify(productRepository, never()).deleteById(any());
    }
}
