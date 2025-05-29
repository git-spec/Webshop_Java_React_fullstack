package org.example.backend.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.backend.model.Product;
import org.example.backend.model.Category;
import org.example.backend.model.Color;
import org.example.backend.model.Currency;
import org.example.backend.model.Dimension;
import org.example.backend.model.Family;
import org.example.backend.model.Group;
import org.example.backend.model.Images;
import org.example.backend.model.Measure;
import org.example.backend.model.ProductFeatures;
import org.example.backend.model.Unit;
import org.example.backend.repository.ProductRepo;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepo prod1Repo;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    Measure measure1 = new Measure(
                                    50,
                                    Unit.CM
                                );
    ProductFeatures feat1 = new ProductFeatures(
                        new Dimension(measure1, measure1, measure1),
                        new Measure(10, Unit.KG),
                        List.of(Color.OAK, Color.BEECH, Color.BLACK)
                    );
              
    Product prod1 = 
            Product.builder()
                .id("id1")
                .number("456")
                .name("Prod1")
                .category(Category.FURNITURE)
                .group(Group.SEATING)
                .family(Family.CHAIR)
                .features(feat1)
                .info("Info")
                .description("Description")
                .images(new Images(
                    "http://image1.test",
                    "http://image2.test",
                    "http://image3.test"
                ))
                .price(BigDecimal.valueOf(123.45))
                .currency(Currency.EUR)
                .amount(100)
                .build();

    @Test
    void getProducts_shouldReturnListOfProduct_whenIsCalled() throws Exception {
        // GIVEN
        prod1Repo.save(prod1);
        List<Product> expected = Collections.singletonList(prod1);
        // THEN
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void getProductsByCategory_shouldReturnListOfCategory_whenGetCategory() throws Exception {
        // GIVEN
        prod1Repo.save(prod1);
        List<Product> expected = Collections.singletonList(prod1);
        // WHEN // THEN
        mockMvc.perform(get("/api/products/furniture"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }
}
