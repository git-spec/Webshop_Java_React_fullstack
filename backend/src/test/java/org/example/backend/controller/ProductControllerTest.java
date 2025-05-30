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
import org.example.backend.model.Material;
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
    Measure width = new Measure(
                                    49,
                                    Unit.CM
                                );
    Measure length = new Measure(
                                    45,
                                    Unit.CM
                                );
    Measure height = new Measure(
                                    78,
                                    Unit.CM
                                );
    ProductFeatures feat1 = new ProductFeatures(
                        new Dimension(width, length, height),
                        new Measure(10, Unit.KG),
                        List.of(Material.OAK, Material.ASH),
                        List.of(Color.OAK, Color.ASH, Color.BLACK)
                    );
              
    Product prod1 = 
            Product.builder()
                .id("1536716")
                .number("1990")
                .name("Lara Chair")
                .manufacturer("Erol")
                .category(Category.FURNITURE)
                .group(Group.SEATING)
                .family(Family.CHAIR)
                .features(feat1)
                .info("Info")
                .description("Description")
                .images(new Images(
                    "/public/small/lara/Lara--1990--chair--cutoutAngle-2--Ash--CM.jpg",
                    "http://image2.test",
                    "/public/large/lara/Lara--1990--chair--cutoutAngle-2--Ash--CM.jpg"
                ))
                .price(BigDecimal.valueOf(370))
                .currency(Currency.GBP)
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
