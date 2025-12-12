package com.cosmocats.marketplace.web;

import com.cosmocats.marketplace.dto.ProductDto;
import com.cosmocats.marketplace.service.ProductService;
import com.cosmocats.marketplace.service.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto productDto;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        productDto = ProductDto.builder()
                .id(productId)
                .name("Cosmic Milk")
                .description("Fresh from Milky Way")
                .price(10.0)
                .stock(50)
                .build();
    }

    @Test
    void getAllProducts_shouldReturn200() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(productDto));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cosmic Milk"));
    }

    @Test
    void getProductById_found_shouldReturn200() throws Exception {
        when(productService.getProductById(productId)).thenReturn(productDto);

        mockMvc.perform(get("/api/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId.toString()));
    }

    @Test
    void getProductById_notFound_shouldReturn404() throws Exception {
        when(productService.getProductById(productId))
                .thenThrow(new ProductNotFoundException(productId));

        mockMvc.perform(get("/api/v1/products/{id}", productId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createProduct_valid_shouldReturn200() throws Exception {
        ProductDto createDto = ProductDto.builder()
                .name("Star Dust")
                .price(25.0)
                .stock(100)
                .build();

        when(productService.createProduct(any())).thenReturn(productDto);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk());
    }

    @Test
    void createProduct_blankName_shouldReturn400() throws Exception {
        ProductDto invalid = ProductDto.builder()
                .name("")
                .price(10.0)
                .stock(50)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProduct_noCosmicWord_shouldReturn400() throws Exception {
        ProductDto invalid = ProductDto.builder()
                .name("Regular Milk")
                .price(5.0)
                .stock(50)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProduct_negativePrice_shouldReturn400() throws Exception {
        ProductDto invalid = ProductDto.builder()
                .name("Cosmic Milk")
                .price(-10.0)
                .stock(50)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProduct_negativeStock_shouldReturn400() throws Exception {
        ProductDto invalid = ProductDto.builder()
                .name("Cosmic Milk")
                .price(10.0)
                .stock(-5)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct_valid_shouldReturn200() throws Exception {
        when(productService.updateProductById(eq(productId), any()))
                .thenReturn(productDto);

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct_notFound_shouldReturn404() throws Exception {
        when(productService.updateProductById(eq(productId), any()))
                .thenThrow(new ProductNotFoundException(productId));

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_found_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_notFound_shouldReturn404() throws Exception {
        doThrow(new ProductNotFoundException(productId))
                .when(productService).deleteProductById(productId);

        mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isNotFound());
    }
}
