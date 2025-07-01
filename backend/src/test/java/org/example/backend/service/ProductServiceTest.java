package org.example.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.example.backend.exception.NotFoundException;
import org.example.backend.model.Category;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                        List.of("oak", "ash", "black")
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
                        List.of("/public/small/lara/Lara--1990--chair--cutoutAngle-2--Ash--CM.jpg"),
                        List.of("http://imag)e2.test"),
                        List.of("/public/large/lara/Lara--1990--chair--cutoutAngle-2--Ash--)M.jpg")
                    )
                )
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
        // GIVEN
        String expected = "Seite nicht gefunden.";
        // WHEN // THEN
        NotFoundException result = assertThrows(
            NotFoundException.class, 
            () -> productService.getProductsByCategoryAndGroup("FEHLER", "HAFT")
        );
        assertEquals(expected, result.getMessage());
    }

    @Test
    void getProductsByCategoryAndGroupAndFamily_shouldReturnListOfFamily_whenGetFamily() throws NotFoundException {
        // GIVEN
        when(
            productRepo.findAllByCategoryAndGroupAndFamily(Category.FURNITURE.toString(), 
            Group.SEATING.toString(), 
            Family.CHAIR.toString())).thenReturn(List.of(prod1)
        );
        // WHEN
        List<Product> actual = productService.getProductsByCategoryAndGroupAndFamily(
            Category.FURNITURE.toString(), 
            Group.SEATING.toString(),
            Family.CHAIR.toString()
        );
        // THEN
        verify(productRepo).findAllByCategoryAndGroupAndFamily(
            Category.FURNITURE.toString(), 
            Group.SEATING.toString(), 
            Family.CHAIR.toString()
        );
        assertEquals(List.of(prod1), actual);
    }

    @Test
    void getProductsByCategoryAndGroupAndFamily_shouldReturnEmptyArray_whenNoFamilyExist() throws NotFoundException {
        // GIVEN
        when(productRepo.findAllByCategoryAndGroupAndFamily(
            Category.FURNITURE.toString(), 
            Group.SEATING.toString(), 
            Family.CHAIR.toString())
        ).thenReturn(List.of());
        // WHEN
        List<Product> actual = productService.getProductsByCategoryAndGroupAndFamily(
            Category.FURNITURE.toString(), 
            Group.SEATING.toString(), 
            Family.CHAIR.toString()
        );
        // THEN
        verify(productRepo).findAllByCategoryAndGroupAndFamily(
            Category.FURNITURE.toString(), 
            Group.SEATING.toString(), 
            Family.CHAIR.toString()
        );
        assertDoesNotThrow(
            () -> productService.getProductsByCategoryAndGroupAndFamily(
                Category.FURNITURE.toString(), 
                Group.SEATING.toString(), 
                Family.CHAIR.toString()
            )
        );
        assertEquals(List.of(), actual);
    }

    @Test
    void getProductsByCategoryAndGroupAndFamily_shouldThrowNotFoundException_whenInvalidFamily() {
        // GIVEN
        String expected = "Seite nicht gefunden.";
        // WHEN // THEN
        NotFoundException result = assertThrows(
            NotFoundException.class, 
            () -> productService.getProductsByCategoryAndGroupAndFamily("FURNITURE", "SEATING", "FEHLER")
        );
        assertEquals(expected, result.getMessage());
    }

    @Test
    void getProductByID_shouldReturnProduct_whenGetID() {
        // GIVEN
        String id = "1536716";
        // WHEN
        when(productRepo.findById(id)).thenReturn(Optional.of(prod1));
        Optional<Product> actual = productService.getProductByID(id);
        // THEN
        assertEquals(Optional.of(prod1), actual);
    }

    @Test
    void getProductByID_shouldReturnEmptyOptional_whenNotFound() {
        // GIVEN
        String id = "15161718";
        // WHEN
        when(productRepo.findById(id)).thenReturn(Optional.empty());
        Optional<Product> actual = productService.getProductByID(id);
        // THEN
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void getProductByID_shouldThrowException_whenIDisNull() {
        // GIVEN
        String id = null;
        // WHEN
        when(productRepo.findById(id)).thenThrow(IllegalArgumentException.class);
        // THEN
        IllegalArgumentException result = assertThrows(
            IllegalArgumentException.class, 
            () -> productService.getProductByID(id)
        );
        assertNotNull(result);
    }
}
