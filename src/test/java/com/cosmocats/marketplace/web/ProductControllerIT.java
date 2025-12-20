package com.cosmocats.marketplace.web;

import com.cosmocats.marketplace.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID createdProductId;

    @BeforeEach
    void setUp() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .name("Cosmic Milk")
                .price(10.0)
                .stock(50)
                .build();

        String response = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        ProductDto created = objectMapper.readValue(response, ProductDto.class);
        createdProductId = created.id();
    }

    @Test
    void getAllProducts_shouldReturn200WithProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[2].name").value("Laser Pointer 9000"))
                .andExpect(jsonPath("$[3].name").value("Cosmic Milk"));
    }

    @Test
    void getProductById_existingProduct_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/products/{id}", createdProductId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdProductId.toString()))
                .andExpect(jsonPath("$.name").value("Cosmic Milk"));
    }

    @Test
    void getProductById_nonExistingProduct_shouldReturn404() throws Exception {
        UUID nonExistingId = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/products/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Product Not Found"));
    }

    @Test
    void createProduct_validData_shouldReturn200() throws Exception {
        ProductDto newProduct = ProductDto.builder()
                .name("Star Dust")
                .price(25.0)
                .stock(100)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Star Dust"));
    }

    @Test
    void createProduct_invalidData_shouldReturn400() throws Exception {
        ProductDto invalid = ProductDto.builder()
                .name("")
                .price(-10.0)
                .stock(-5)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void createProduct_missingCosmicWord_shouldReturn400() throws Exception {
        ProductDto invalid = ProductDto.builder()
                .name("Regular Milk")
                .price(5.0)
                .stock(50)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void updateProduct_existingProduct_shouldReturn200() throws Exception {
        ProductDto update = ProductDto.builder()
                .name("Cosmic Milk Premium")
                .price(15.0)
                .stock(30)
                .build();

        mockMvc.perform(put("/api/v1/products/{id}", createdProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdProductId.toString()))
                .andExpect(jsonPath("$.name").value("Cosmic Milk Premium"));
    }

    @Test
    void updateProduct_nonExistingProduct_shouldReturn404() throws Exception {
        UUID nonExistingId = UUID.randomUUID();
        ProductDto update = ProductDto.builder()
                .name("Cosmic Milk")
                .price(10.0)
                .stock(50)
                .build();

        mockMvc.perform(put("/api/v1/products/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void deleteProduct_existingProduct_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/products/{id}", createdProductId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_nonExistingProduct_shouldReturn404() throws Exception {
        UUID nonExistingId = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/products/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
