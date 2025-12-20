package com.cosmocats.marketplace.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record CartDto(UUID id, String customerEmail, LocalDateTime createdAt, LocalDateTime updatedAt,
    @Singular @Valid List<CartItemDto> cartItems, Double totalAmount, Integer totalItems) {
}
