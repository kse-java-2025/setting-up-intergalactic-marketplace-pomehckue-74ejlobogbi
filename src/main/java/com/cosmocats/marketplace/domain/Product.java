package com.cosmocats.marketplace.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Product {
    
    Long id;
    String name;
    String description;
    Double price;
    String currency;
    Integer stock;
    Category category;
}
