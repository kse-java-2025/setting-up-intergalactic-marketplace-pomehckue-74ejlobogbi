package com.cosmocats.marketplace.domain;

import lombok.Value;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class Cart {
    
    UUID id;
    String customerEmail;
    List<CartItem> cartItems = new ArrayList<>();

    public Double getTotalAmount() {
        return cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public Integer getTotalItems() {
        return cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
