package com.cosmocats.marketplace.web;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.service.ProductService;
import com.cosmocats.marketplace.service.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product sampleProduct;
    private UUID sampleId;

    @BeforeEach
    void setUp() {
        sampleId = UUID.randomUUID();
        sampleProduct = Product.builder()
                .id(sampleId)
                .name("Cosmic Milk")
                .description("Delicious cosmic milk")
                .price(5.0)
                .currency("USD")
                .stock(10)
                .build();
    }

    @Test
    void getAllProducts_shouldReturnProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(sampleProduct));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleId.toString()));
    }

    @Test
    void getProductById_shouldReturnProduct() throws Exception {
        when(productService.getProductById(sampleId)).thenReturn(sampleProduct);

        mockMvc.perform(get("/api/v1/products/{id}", sampleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleId.toString()))
                .andExpect(jsonPath("$.name").value("Cosmic Milk"));
    }

    @Test
    void getProductById_notFound_shouldReturn404() throws Exception {
        when(productService.getProductById(sampleId))
                .thenThrow(new ProductNotFoundException(sampleId));

        mockMvc.perform(get("/api/v1/products/{id}", sampleId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Product with id " + sampleId + " not found"));
    }

    @Test
    void createProduct_validInput_shouldReturnProduct() throws Exception {
        ProductDto dto = new ProductDto();
        dto.name = "Star Toy";
        dto.description = "Awesome cosmic toy";
        dto.price = 10.0;
        dto.currency = "USD";
        dto.stock = 3;

        Product created = Product.builder()
                .id(UUID.randomUUID())
                .name(dto.name)
                .description(dto.description)
                .price(dto.price)
                .currency(dto.currency)
                .stock(dto.stock)
                .build();

        when(productService.createProduct(any())).thenReturn(created);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId().toString()));
    }

    @Test
    void createProduct_invalidInput_shouldReturnBadRequest() throws Exception {
        ProductDto dto = new ProductDto();
        dto.name = "";
        dto.price = -1;
        dto.currency = "USD";
        dto.stock = 1;

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void deleteProduct_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/products/{id}", sampleId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_notFound_shouldReturn404() throws Exception {
        doThrow(new ProductNotFoundException(sampleId))
                .when(productService).deleteProductById(sampleId);

        mockMvc.perform(delete("/api/v1/products/{id}", sampleId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Product with id " + sampleId + " not found"));
    }
}
