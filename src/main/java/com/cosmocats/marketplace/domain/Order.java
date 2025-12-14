package com.cosmocats.marketplace.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class Order {
    
    Long id;
    String customerName;
    String customerEmail;

    @Builder.Default
    OrderStatus status = OrderStatus.PENDING;

    Double totalAmount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @Builder.Default
    List<OrderItem> orderItems = new ArrayList<>();

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }
}
