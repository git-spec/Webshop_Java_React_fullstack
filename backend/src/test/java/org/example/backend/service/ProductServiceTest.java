package org.example.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.example.backend.exception.NotFoundException;
import org.example.backend.model.Category;
import org.example.backend.model.Color;
import org.example.backend.model.Currency;
import org.example.backend.model.Dimension;
import org.example.backend.model.Family;
import org.example.backend.model.Group;
import org.example.backend.model.Images;
import org.example.backend.model.Material;
import org.example.backend.model.Measure;
import org.example.backend.model.Product;
import org.example.backend.model.ProductFeatures;
import org.example.backend.model.Unit;
import org.example.backend.repository.ProductRepo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;


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
    void getProductsByCategory_shouldReturnListOfCategory_whenGetCategory() throws NotFoundException {
        // GIVEN
        when(productRepo.findAllByCategory(Category.FURNITURE.toString())).thenReturn(List.of(prod1));

        // WHEN
        List<Product> actual = productService.getProductsByCategory(Category.FURNITURE.toString());
        // THEN
        verify(productRepo).findAllByCategory(Category.FURNITURE.toString());
        assertDoesNotThrow(() -> productService.getProductsByCategory(Category.FURNITURE.toString()));
        assertEquals(List.of(prod1), actual);
    }

    @Test
    void getProductsByCategory_shouldReturnEmptyArray_whenNoCategoryExist() throws NotFoundException {
        // GIVEN
        when(productRepo.findAllByCategory(Category.FURNITURE.toString())).thenReturn(List.of());
        // WHEN
        List<Product> actual = productService.getProductsByCategory(Category.FURNITURE.toString());
        // THEN
        verify(productRepo).findAllByCategory(Category.FURNITURE.toString());
        assertDoesNotThrow(() -> productService.getProductsByCategory(Category.FURNITURE.toString()));
        assertEquals(List.of(), actual);
    }

    @Test
    void getProductsByCategory_shouldThrowIllegalArgumentException_whenInvalidCategory() {
        // WHEN // THEN
        NotFoundException exception = assertThrows(
            NotFoundException.class, 
            () -> productService.getProductsByCategory("FEHLER")
        );
        assertEquals("Seite nicht gefunden.", exception.getMessage());
    }

    @Test
    void getProductsByCategoryAndGroup_shouldReturnListOfGroup_whenGetGroup() throws NotFoundException {
        // GIVEN
        when(productRepo.findAllByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString())).thenReturn(List.of(prod1));
        // WHEN
        List<Product> actual = productService.getProductsByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString());
        // THEN
        verify(productRepo).findAllByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString());
        assertEquals(List.of(prod1), actual);
    }

    @Test
    void getProductsByCategoryAndGroup_shouldReturnEmptyArray_whenNoGroupExist() throws NotFoundException {
        // GIVEN
        when(productRepo.findAllByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString())).thenReturn(List.of());
        // WHEN
        List<Product> actual = productService.getProductsByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString());
        // THEN
        verify(productRepo).findAllByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString());
        assertDoesNotThrow(() -> productService.getProductsByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString()));
        assertEquals(List.of(), actual);
    }

    @Test
    void getProductsByCategoryAndGroup_shouldThrowNotFoundException_whenInvalidGroup() {
        // WHEN // THEN
        assertThrows(
            NotFoundException.class, 
            () -> productService.getProductsByCategoryAndGroup("FEHLER", "HAFT")
        );
    }
}
