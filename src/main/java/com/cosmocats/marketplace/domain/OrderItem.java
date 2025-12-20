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
public class OrderItem {

    private UUID id;
    private Order order;
    private Product product;
    private Integer quantity;
    private Double priceAtPurchase;

    public Double getSubtotal() {
        return priceAtPurchase * quantity;
    }
}
