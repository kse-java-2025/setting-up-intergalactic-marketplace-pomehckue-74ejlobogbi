package com.cosmocats.marketplace.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderDto(UUID id, @NotBlank(message = "Customer name is required")
    @Size(max = 100, message = "Customer name must not exceed 100 characters") String customerName,
    @NotBlank(message = "Customer email is required") @Email(message = "Invalid email format") String customerEmail,
    String status, Double totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt,
    @Singular @Valid @NotEmpty(message = "Order must contain at least one item") List<OrderItemDto> orderItems) {
}
