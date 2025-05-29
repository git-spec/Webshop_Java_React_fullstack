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
import java.lang.IllegalArgumentException;

import org.example.backend.exception.CategoryNotFoundException;
import org.example.backend.model.Article;
import org.example.backend.model.Category;
import org.example.backend.model.Color;
import org.example.backend.model.Currency;
import org.example.backend.model.Dimension;
import org.example.backend.model.Family;
import org.example.backend.model.Group;
import org.example.backend.model.Images;
import org.example.backend.model.Measure;
import org.example.backend.model.Product;
import org.example.backend.model.Unit;
import org.example.backend.model.ProductFeatures;
import org.example.backend.repository.ProductRepo;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductService productService;

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
                .id("prod_id1")
                .name("Prod1")
                .number("456")
                .category(Category.FURNITURE)
                .group(Group.SEATING)
                .family(Family.CHAIR)
                .features(feat1)
                .description("Description")
                .images(new Images(
                    "http://image1.test",
                    "http://image2.test",
                    "http://image3.test"
                ))
                .build();

    List<Article> articles = List.of(
            Article.builder()
            .id("id1")
            .number("123")
            .product(prod1)
            .price(BigDecimal.valueOf(123.45))
            .currency(Currency.EUR)
            .amount(100)
            .build()
        );
    
    @Test
    void getAllArticles_shouldReturnListOfArticle_whenArticlesExist() {
        // GIVEN  
        when(productRepo.findAll()).thenReturn(articles);
        // WHEN
        List<Article> actual = productService.getArticles();
        // THEN
        assertEquals(articles, actual);
        verify(productRepo).findAll();
    }
    
    @Test
    void getAllArticles_shouldReturnEmtyArray_whenNoArticlesExist() {
        // GIVEN
        when(productRepo.findAll()).thenReturn(List.of());
        // WHEN
        List<Article> actual = productService.getArticles();
        // THEN
        assertEquals(List.of(), actual);
        verify(productRepo).findAll();
    }

    @Test
    void getArticlesByCategory_shouldReturnListOfCategory_whenGetCategory() {
        // GIVEN
        when(productRepo.findAllByProductCategory(Category.FURNITURE.toString())).thenReturn(articles);
        // WHEN
        List<Article> actual = productService.getArticlesByCategory(Category.FURNITURE.toString());
        // THEN
        assertEquals(articles, actual);
        verify(productRepo).findAllByProductCategory(Category.FURNITURE.toString());
    }

    @Test
    void getArticlesByCategory_shouldReturnEmtyArray_whenNoCategoriesExist() {
        // GIVEN
        when(productRepo.findAllByProductCategory(Category.FURNITURE.toString())).thenReturn(List.of());
        // WHEN
        List<Article> actual = productService.getArticlesByCategory(Category.FURNITURE.toString());
        // THEN
        assertEquals(List.of(), actual);
        verify(productRepo).findAllByProductCategory(Category.FURNITURE.toString());
    }

    @Test
    void getArticlesByCategory_shouldThrowCategoryNotFoundException_whenInvalidCategory() {
        // WHEN // THEN
        assertThrows(CategoryNotFoundException.class, () -> productService.getArticlesByCategory("FEHLER"));
    }
}
