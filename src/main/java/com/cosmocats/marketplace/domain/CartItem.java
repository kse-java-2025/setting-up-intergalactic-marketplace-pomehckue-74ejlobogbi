package com.cosmocats.marketplace.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CartItem {
    
    UUID id;
    Cart cart;
    Product product;
    Integer quantity;

    public Double getSubtotal() {
        return product.getPrice() * quantity;
    }
}
