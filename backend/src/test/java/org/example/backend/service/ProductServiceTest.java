package org.example.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.example.backend.Utils;
import org.example.backend.exception.AccessException;
import org.example.backend.exception.InvalidArgumentException;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;



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
    void getProductsByCategory_shouldReturnListOfCategory_whenGetCategory() throws NotFoundException, AccessException {
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
    void getProductsByCategory_shouldReturnEmptyArray_whenNoCategoryExist() throws NotFoundException, AccessException {
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
    void getProductsByCategory_shouldThrowNotFoundException_whenInvalidParam() {
        // GIVEN
        String expected = ProductService.NOT_FOUND_MESSAGE_FORMAT;
        // WHEN // THEN
        NotFoundException result = assertThrows(
            NotFoundException.class, 
            () -> productService.getProductsByCategory("fehler")
        );
        assertEquals(expected, result.getMessage());
    }

    @Test
    void getProductsByCategory_shouldThrowAccessException_whenNoAccess() {
        // GIVEN
        String expected = ProductService.INTERNAL_ERROR;
        // WHEN 
        when(productRepo.findAllByCategory(anyString())).thenThrow(new AccessException(expected));
        // THEN
        AccessException exception = assertThrows(
            AccessException.class, 
            () -> productService.getProductsByCategory("furniture")
        );
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void getProductsByCategory_shouldThrowDataAccessException_whenNoAccess() {
        // WHEN
        when(productRepo.findAllByCategory(anyString()))
            .thenThrow(new DataAccessResourceFailureException("DB down"));        
        // THEN
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            productRepo.findAllByCategory("fehler");
        });
        assertEquals("DB down", exception.getMessage());
    }

    @Test
    void getProductsByCategoryAndGroup_shouldReturnListOfGroup_whenGetGroup() throws NotFoundException, AccessException {
        // GIVEN
        when(productRepo.findAllByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString())).thenReturn(List.of(prod1));
        // WHEN
        List<Product> actual = productService.getProductsByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString());
        // THEN
        verify(productRepo).findAllByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString());
        assertEquals(List.of(prod1), actual);
    }

    @Test
    void getProductsByCategoryAndGroup_shouldReturnEmptyArray_whenNoGroupExist() throws NotFoundException, AccessException {
        // GIVEN
        when(
            productRepo.findAllByCategoryAndGroup(
                Category.FURNITURE.toString(), 
                Group.SEATING.toString()
                )
            ).thenReturn(List.of());
        // WHEN
        List<Product> actual = productService.getProductsByCategoryAndGroup(
            Category.FURNITURE.toString(), 
            Group.SEATING.toString()
        );
        // THEN
        verify(productRepo).findAllByCategoryAndGroup(Category.FURNITURE.toString(), Group.SEATING.toString());
        assertDoesNotThrow(
            () -> productService.getProductsByCategoryAndGroup(
                Category.FURNITURE.toString(), 
                Group.SEATING.toString()
            )
        );
        assertEquals(List.of(), actual);
    }

    @Test
    void getProductsByCategoryAndGroup_shouldThrowNotFoundException_whenInvalidParam() {
        // GIVEN
        String expected = ProductService.NOT_FOUND_MESSAGE_FORMAT;
        // WHEN // THEN
        NotFoundException result = assertThrows(
            NotFoundException.class, 
            () -> productService.getProductsByCategoryAndGroup(
                
         
                       "fehler", 
                "fehler"
            )
        );
        assertEquals(expected, result.getMessage());
    }

    @Test
    void getProductsByCategoryAndGroup_shouldThrowAccessException_whenNoAccess() {
        // GIVEN
        String expected = ProductService.INTERNAL_ERROR;
        // WHEN 
        when(productRepo.findAllByCategoryAndGroup(anyString(), anyString())).thenThrow(new AccessException(expected));
        // THEN
        AccessException result = assertThrows(
            AccessException.class, 
            () -> productService.getProductsByCategoryAndGroup("furniture", "seating")
        );
        assertEquals(expected, result.getMessage());
    }

    @Test
    void getProductsByCategoryAndGroup_shouldThrowDataAccessException_whenNoAccess() {
        // WHEN
        when(productRepo.findAllByCategoryAndGroup(anyString(), anyString()))
            .thenThrow(new DataAccessResourceFailureException("DB down"));        
        // THEN
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            productRepo.findAllByCategoryAndGroup("fehler", "fehler");
        });
        assertEquals("DB down", exception.getMessage());
    }

    @Test
    void getProductsByCategoryAndGroupAndFamily_shouldReturnListOfFamily_whenGetFamily() throws NotFoundException, AccessException {
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
    void getProductsByCategoryAndGroupAndFamily_shouldReturnEmptyArray_whenNoFamilyExist() throws NotFoundException, AccessException {
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
    void getProductsByCategoryAndGroupAndFamily_shouldThrowNotFoundException_whenInvalidParam() {
        // GIVEN
        String expected = ProductService.NOT_FOUND_MESSAGE_FORMAT;
        // WHEN // THEN
        NotFoundException result = assertThrows(
            NotFoundException.class, 
            () -> productService.getProductsByCategoryAndGroupAndFamily(
                "fehler", 
                "fehler", 
                "fehler"
            )
        );
        assertEquals(expected, result.getMessage());
    }

    @Test
    void getProductsByCategoryAndGroupAndFamily_shouldThrowAccessException_whenNoAccess() {
        // GIVEN
        String expected = ProductService.INTERNAL_ERROR;
        // WHEN 
        when(
            productRepo.findAllByCategoryAndGroupAndFamily(
                anyString(), 
                anyString(), 
                anyString()
                )
            ).thenThrow(new AccessException(expected));
        // THEN
        AccessException result = assertThrows(
            AccessException.class, 
            () -> productService.getProductsByCategoryAndGroupAndFamily(
                "furniture", 
                "seating", 
                "chair"
            )
        );
        assertEquals(expected, result.getMessage());
    }

    @Test
    void getProductsByCategoryAndGroupAndFamily_shouldThrowDataAccessException_whenNoAccess() {
        // WHEN
        when(productRepo.findAllByCategoryAndGroupAndFamily(anyString(), anyString(), anyString()))
            .thenThrow(new DataAccessResourceFailureException("DB down"));        
        // THEN
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            productRepo.findAllByCategoryAndGroupAndFamily(
                "fehler", 
                "fehler", 
                "fehler"
            );
        });
        assertEquals("DB down", exception.getMessage());
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
        when(productRepo.findById(id)).thenThrow(InvalidArgumentException.class);
        // THEN
        InvalidArgumentException result = assertThrows(
            InvalidArgumentException.class, 
            () -> productService.getProductByID(id)
        );
        assertNotNull(result);
    }

    @Test
    void getProductsByID_shouldReturnProducts_whenGetIDs() {
        // GIVEN
        List<String> ids = List.of("1536716");
        // WHEN
        when(productRepo.findByIdIn(ids)).thenReturn(Optional.of(List.of(prod1)));
        List<Product> actual = productService.getProductsByID(ids);
        // THEN
        assertEquals(List.of(prod1), actual);
    }

    @Test
    void getProductsByID_shouldThrowIllegalArgumentException_whenGetInvalidIDs() {
        // GIVEN
        List<String> ids = List.of("1@$");
        List<Boolean> validation = ids.stream().map(id -> Utils.isValidAlphanumeric(id)).toList();
        // WHEN // THEN
        assertTrue(validation.contains(false));
        InvalidArgumentException exception = assertThrows(
            InvalidArgumentException.class, 
            () -> productService.getProductsByID(ids)
        );
        assertEquals(ProductService.ILLEGAL_ARGUMENT, exception.getMessage());
    }

    @Test
    void getProductsByID_shouldThrowAccessException_whenGetInvalidIDs() {
        //GIVEN
        Optional<Object> optinal = Optional.empty();
        // WHEN // THEN
        AccessException exception = assertThrows(
            AccessException.class, 
            () -> optinal.orElseThrow(
                () -> new AccessException(ProductService.NOT_FOUND_MESSAGE_FORMAT)
            )
        );
        assertEquals(ProductService.NOT_FOUND_MESSAGE_FORMAT, exception.getMessage());
    }
}
