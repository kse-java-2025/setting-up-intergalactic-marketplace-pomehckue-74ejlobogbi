package com.cosmocats.marketplace.controller;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }
}

