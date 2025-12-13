package com.cosmocats.marketplace.domain;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class Category {
    
    Long id;
    String name;
    String description;

    @Builder.Default
    List<Product> products = new ArrayList<>();
}
