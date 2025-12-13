package com.cosmocats.marketplace.client;

import com.cosmocats.marketplace.client.dto.PriceValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class SupplierClient {

    private final RestClient restClient;

    public SupplierClient(@Value("${supplier.api.url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public boolean validatePrice(String productName, Double price) {
        log.info("Validating price for product: {}", productName);

        try {
            PriceValidationResponse response = restClient.get()
                    .uri("/api/validate-price?name={name}&price={price}", productName, price)
                    .retrieve()
                    .body(PriceValidationResponse.class);

            boolean valid = response != null && response.isValid();
            log.info("Price validation result: {}", valid);
            return valid;

        } catch (Exception e) {
            log.warn("Price validation failed: {}", e.getMessage());
            return true;
        }
    }
}
