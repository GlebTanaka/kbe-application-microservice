package de.htwberlin.f4.applicationmicroservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import de.htwberlin.f4.applicationmicroservice.dao.ProductRepository;
import de.htwberlin.f4.applicationmicroservice.models.product.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIntTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getSimpleProducts() throws Exception {
        List<Product> products = productRepository.findAll();
        
        MvcResult mvcR = mvc.perform(get("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List simpleProducts = objectMapper.readValue(Objects.requireNonNull(mvcR).getResponse().getContentAsString(), List.class);

        assertEquals(10, simpleProducts.size());
        assertTrue(simpleProducts.toString().contains("name=" + products.get(0).getName()));
        assertTrue(simpleProducts.toString().contains("name=" + products.get(1).getName()));
        assertTrue(simpleProducts.toString().contains("id=" + products.get(0).getId()));
        assertTrue(simpleProducts.toString().contains("id=" + products.get(1).getId()));
        assertFalse(simpleProducts.toString().contains("description="));
    }
}
