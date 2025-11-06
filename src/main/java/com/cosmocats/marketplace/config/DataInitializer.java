package com.cosmocats.marketplace.config;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        Product product1 = new Product();
        product1.setName("Anti-Gravity Yarn Ball");
        product1.setDescription("Perfect for cosmic cats! Floats in zero gravity.");
        product1.setPrice(299.99);

        Product product2 = new Product();
        product2.setName("Cosmic Milk");
        product2.setDescription("Freshly collected from the Milky Way galaxy.");
        product2.setPrice(49.99);

        Product product3 = new Product();
        product3.setName("Laser Pointer 9000");
        product3.setDescription("Entertains cats across multiple dimensions.");
        product3.setPrice(149.50);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }
}
