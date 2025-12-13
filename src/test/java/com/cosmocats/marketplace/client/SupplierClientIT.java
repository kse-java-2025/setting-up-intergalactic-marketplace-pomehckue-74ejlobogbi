package com.cosmocats.marketplace.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@WireMockTest(httpPort = 9090)
class SupplierClientIT {

    @Autowired
    private SupplierClient supplierClient;

    @Test
    void validatePrice_shouldReturnTrue() {
        stubFor(get(urlPathMatching("/api/validate-price.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"valid\": true, \"message\": \"OK\"}")));

        boolean result = supplierClient.validatePrice("Cosmic Milk", 10.0);

        assertTrue(result);
        WireMock.verify(getRequestedFor(urlPathMatching("/api/validate-price.*")));
    }
}
