package com.cosmocats.marketplace.dto;

import com.cosmocats.marketplace.validation.CosmicWordCheck;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductDto(UUID id, @CosmicWordCheck @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must not exceed 100 characters") String name,
    @Size(max = 2000, message = "Description must not exceed 2000 characters") String description,
    @NotNull(message = "Price is required") @DecimalMin(value = "0.01", message = "Price must be at least 0.01") Double price,
    @NotNull(message = "Stock is required") @Min(value = 0, message = "Stock cannot be negative") Integer stock, UUID categoryId) {
}
