package com.cosmocats.marketplace.config;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        log.info("Initializing test data...");

        Product product1 = Product.builder()
                .name("Anti-Gravity Yarn Ball")
                .description("Perfect for cosmic cats! Floats in zero gravity")
                .price(299.99)
                .stock(15)
                .build();

        Product product2 = Product.builder()
                .name("Cosmic Milk")
                .description("Freshly collected from the Milky Way galaxy")
                .price(49.99)
                .stock(50)
                .build();

        Product product3 = Product.builder()
                .name("Laser Pointer 9000")
                .description("Entertains cats across multiple dimensions")
                .price(149.50)
                .stock(25)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        log.info("Test data initialized successfully. Created {} products", productRepository.count());
    }
}
