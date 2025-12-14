package com.cosmocats.marketplace.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderItem {
    
    Long id;
    Product product;
    Integer quantity;
    Double priceAtPurchase;

    public Double getSubtotal() {
        return priceAtPurchase * quantity;
    }
}
