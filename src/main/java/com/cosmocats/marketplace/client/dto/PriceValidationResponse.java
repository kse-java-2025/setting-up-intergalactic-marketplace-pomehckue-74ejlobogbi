package com.cosmocats.marketplace.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceValidationResponse {
    private boolean valid;
    private String message;
}
