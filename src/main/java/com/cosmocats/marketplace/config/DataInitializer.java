package com.cosmocats.marketplace.config;

import com.cosmocats.marketplace.repository.ProductRepository;
import com.cosmocats.marketplace.repository.entity.CategoryEntity;
import com.cosmocats.marketplace.repository.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initializing test data...");

        CategoryEntity toysCategory = CategoryEntity.builder()
                .name("Cosmic Toys")
                .description("Toys for space cats")
                .categoryReference(UUID.randomUUID())
                .build();

        ProductEntity product1 = ProductEntity.builder()
                .name("Anti-Gravity Yarn Ball")
                .description("Perfect for cosmic cats! Floats in zero gravity")
                .price(299.99)
                .currency("USD")
                .stock(15)
                .category(toysCategory)
                .productReference(UUID.randomUUID())
                .build();

        ProductEntity product2 = ProductEntity.builder()
                .name("Cosmic Milk")
                .description("Freshly collected from the Milky Way galaxy")
                .price(49.99)
                .currency("USD")
                .stock(50)
                .category(toysCategory)
                .productReference(UUID.randomUUID())
                .build();

        ProductEntity product3 = ProductEntity.builder()
                .name("Laser Pointer 9000")
                .description("Entertains cats across multiple dimensions")
                .price(149.50)
                .currency("USD")
                .stock(25)
                .category(toysCategory)
                .productReference(UUID.randomUUID())
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        log.info("Test data initialized successfully. Created {} products", productRepository.count());
    }
}
