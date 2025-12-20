package com.cosmocats.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryDto(UUID id, @NotBlank(message = "Category name cannot be blank")
    @Size(max = 100, message = "Category name must not exceed 100 characters") String name,
    @Size(max = 500, message = "Description must not exceed 500 characters") String description) {
}
