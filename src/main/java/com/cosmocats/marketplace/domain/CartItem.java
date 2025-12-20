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
public class CartItem {

    private UUID id;
    private Cart cart;
    private Product product;
    private Integer quantity;

    public Double getSubtotal() {
        return product.getPrice() * quantity;
    }
}
