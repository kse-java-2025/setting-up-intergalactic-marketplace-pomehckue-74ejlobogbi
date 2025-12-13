package com.cosmocats.marketplace.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private UUID id;

    private String customerEmail;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Valid
    @Builder.Default
    private List<CartItemDto> cartItems = new ArrayList<>();

    private Double totalAmount;

    private Integer totalItems;
}
