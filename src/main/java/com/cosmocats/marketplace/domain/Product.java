package com.cosmocats.marketplace.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Category category;
}
