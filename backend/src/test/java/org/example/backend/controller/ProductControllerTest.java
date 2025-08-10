package org.example.backend.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.example.backend.model.Product;
import org.example.backend.exception.NotFoundException;
import org.example.backend.model.Category;
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
import org.example.backend.service.ProductService;


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
    @MockitoBean
    private  ProductService mockService;

    String id = "1536716";
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
                        List.of("oak", "ash", "black")
                    );        
    Product prod1 = 
            Product.builder()
                .id(id)
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
                    List.of("/small/lara/Lara--1990--chair--cutoutAngle-2--Ash--CM.jpg"),
                    List.of("http://imag)e2.test"),
                    List.of("/large/lara/Lara--1990--chair--cutoutAngle-2--Ash-)-CM.jpg")
                ))
                .price(BigDecimal.valueOf(370))
                .currency(Currency.GBP)
                .amount(100)
                .build();
    private static final String NOT_FOUND_MESSAGE_FORMAT = "Seite nicht gefunden.";

    @Test
    @WithMockUser
    void getProducts_shouldReturnListOfProduct_whenIsCalled() throws Exception {
        // GIVEN
        prod1Repo.save(prod1);
        List<Product> expected = Collections.singletonList(prod1);
        // WHEN
        when(mockService.getProducts()).thenReturn(expected);;
        // THEN
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @WithMockUser
    void getProductsByCategory_shouldReturnListOfCategory_whenGetCategory() throws Exception {
        // GIVEN
        prod1Repo.save(prod1);
        List<Product> expected = Collections.singletonList(prod1);
        // WHEN
        when(mockService.getProductsByCategory(anyString())).thenReturn(expected);
        // THEN
        mockMvc.perform(get("/api/products/furniture"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @WithMockUser
    void getProductsByCategory_shouldReturn404_whenGetInvalidCategory() throws Exception {
        // WHEN 
        when(mockService.getProductsByCategory("invalid")).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE_FORMAT));
        // THEN
        mockMvc.perform(get("/api/products/{category}", "invalid"))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
            .andExpect(content().string(NOT_FOUND_MESSAGE_FORMAT));
    }

    @Test
    @WithMockUser
    void getProductsByCategoryAndGroup_shouldReturnListOfGroup_whenGetGroup() throws Exception {
        // GIVEN
        prod1Repo.save(prod1);
        List<Product> expected = Collections.singletonList(prod1);
        // WHEN
        when(mockService.getProductsByCategoryAndGroup(
                anyString(),
                anyString()
            )).thenReturn(expected);
        // THEN
        mockMvc.perform(get("/api/products/furniture/seating"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @WithMockUser
    void getProductsByCategoryAndGroup_shouldReturn404_whenGetInvalidCategory() throws Exception {
        // WHEN 
        when(mockService.getProductsByCategoryAndGroup("invalid", "invalid"))
            .thenThrow(new NotFoundException(NOT_FOUND_MESSAGE_FORMAT));
        // THEN
        mockMvc.perform(get("/api/products/{category}/{group}", "invalid", "invalid"))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
            .andExpect(content().string(NOT_FOUND_MESSAGE_FORMAT));
    }

    @Test
    @WithMockUser
    void getProductsByCategoryAndGroupAndFamily_shouldReturnListOfGroup_whenGetGroup() throws Exception {
        // GIVEN
        prod1Repo.save(prod1);
        List<Product> expected = Collections.singletonList(prod1);
        // WHEN
        when(mockService.getProductsByCategoryAndGroupAndFamily(
                anyString(),
                anyString(),
                anyString()
            )
        ).thenReturn(expected);
        // THEN
        mockMvc.perform(get("/api/products/furniture/seating/chair"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @WithMockUser
    void getProductsByCategoryAndGroupAndFamily_shouldReturn404_whenGetInvalidCategory() throws Exception {
        // WHEN 
        when(mockService.getProductsByCategoryAndGroupAndFamily("invalid", "invalid", "invalid"))
            .thenThrow(new NotFoundException(NOT_FOUND_MESSAGE_FORMAT));
        // THEN
        mockMvc.perform(get("/api/products/{category}/{group}/{chair}", "invalid", "invalid", "invalid"))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
            .andExpect(content().string(NOT_FOUND_MESSAGE_FORMAT));
    }

    @Test
    @WithMockUser
    void getProductByID_shouldReturnProduct_whenGetID() throws Exception {
        // GIVEN
        prod1Repo.save(prod1);
        Optional<Product> expected = Optional.of(prod1);
        // WHEN
        when(mockService.getProductByID(id)).thenReturn(expected);
        // THEN
        mockMvc.perform(get("/api/product/{id}", id))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @WithMockUser
    void getProductByID_shouldReturnEmptyOptional_whenGetInvalidID() throws Exception {
        // GIVEN
        Optional<Object> expected = Optional.empty();
        // WHEN // THEN
        mockMvc.perform(get("/api/product/{id}", "123"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @WithMockUser
    void getProductsByID_shouldReturnProducts_whenGetIDs() throws Exception {
        // GIVEN
        prod1Repo.save(prod1);
        List<Product> expected = List.of(prod1);
        // WHEN
        when(mockService.getProductsByID(List.of(id))).thenReturn(expected);
        // THEN
        mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(
                """
                    [
                        "1536716"
                    ]
                """
            )).andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }
}
