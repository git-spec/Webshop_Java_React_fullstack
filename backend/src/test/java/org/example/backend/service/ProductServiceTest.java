package org.example.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.example.backend.exception.CategoryNotFoundException;
import org.example.backend.model.Product;
import org.example.backend.model.Category;
import org.example.backend.model.Family;
import org.example.backend.model.Group;
import org.example.backend.model.Color;
import org.example.backend.model.Currency;
import org.example.backend.model.Dimension;
import org.example.backend.model.Images;
import org.example.backend.model.Material;
import org.example.backend.model.Measure;
import org.example.backend.model.Unit;
import org.example.backend.model.ProductFeatures;
import org.example.backend.repository.ProductRepo;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductService productService;

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
                        new Measure(6, Unit.KG),
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
    void getAllProducts_shouldReturnListOfProduct_whenProductsExist() {
        // GIVEN  
        when(productRepo.findAll()).thenReturn(List.of(prod1));
        // WHEN
        List<Product> actual = productService.getProducts();
        // THEN
        assertEquals(List.of(prod1), actual);
        verify(productRepo).findAll();
    }
    
    @Test
    void getAllProducts_shouldReturnEmtyArray_whenNoProductsExist() {
        // GIVEN
        when(productRepo.findAll()).thenReturn(List.of());
        // WHEN
        List<Product> actual = productService.getProducts();
        // THEN
        assertEquals(List.of(), actual);
        verify(productRepo).findAll();
    }

    @Test
    void getProductsByCategory_shouldReturnListOfCategory_whenGetCategory() {
        // GIVEN
        when(productRepo.findAllByCategory(Category.FURNITURE.toString())).thenReturn(List.of(prod1));
        // WHEN
        List<Product> actual = productService.getProductsByCategory(Category.FURNITURE.toString());
        // THEN
        assertEquals(List.of(prod1), actual);
        verify(productRepo).findAllByCategory(Category.FURNITURE.toString());
    }

    @Test
    void getProductsByCategory_shouldReturnEmptyArray_whenNoCategoryExist() {
        // GIVEN
        when(productRepo.findAllByCategory(Category.FURNITURE.toString())).thenReturn(List.of());
        // WHEN
        List<Product> actual = productService.getProductsByCategory(Category.FURNITURE.toString());
        // THEN
        assertEquals(List.of(), actual);
        verify(productRepo).findAllByCategory(Category.FURNITURE.toString());
    }

    @Test
    void getProductsByCategory_shouldThrowCategoryNotFoundException_whenInvalidCategory() {
        // WHEN // THEN
        assertThrows(CategoryNotFoundException.class, () -> productService.getProductsByCategory("FEHLER"));
    }

    @Test
    void getProductsByGroup_shouldReturnListOfGroup_whenGetGroup() {
        // GIVEN
        when(productRepo.findAllByGroup(Group.SEATING.toString())).thenReturn(List.of(prod1));
        // WHEN
        List<Product> actual = productService.getProductsByGroup(Group.SEATING.toString());
        // THEN
        assertEquals(List.of(prod1), actual);
        verify(productRepo).findAllByGroup(Group.SEATING.toString());
    }

    @Test
    void getProductsByGroup_shouldReturnEmptyArray_whenNoGroupExist() {
        // GIVEN
        when(productRepo.findAllByGroup(Group.SEATING.toString())).thenReturn(List.of());
        // WHEN
        List<Product> actual = productService.getProductsByGroup(Group.SEATING.toString());
        // THEN
        assertEquals(List.of(), actual);
        verify(productRepo).findAllByGroup(Group.SEATING.toString());
    }

    @Test
    void getProductsByGroup_shouldThrowGroupNotFoundException_whenInvalidGroup() {
        // WHEN // THEN
        assertThrows(CategoryNotFoundException.class, () -> productService.getProductsByGroup("FEHLER"));
    }
}
