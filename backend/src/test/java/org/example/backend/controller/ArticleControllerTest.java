package org.example.backend.controller;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.example.backend.model.ProductFeatures;
import org.example.backend.model.Unit;
import org.example.backend.repository.ArticleRepo;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArticleRepo articleRepo;
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
    Article article = Article.builder()
            .id("id1")
            .number("123")
            .product(prod1)
            .price(BigDecimal.valueOf(123.45))
            .currency(Currency.EUR)
            .amount(100)
            .build();

    @Test
    void getArticles_shouldReturnListOfArticle_whenIsCalled() throws Exception {
        // GIVEN
        articleRepo.save(article);
        // THEN
        mockMvc.perform(get("/api/articles"))
            .andExpect(status().isOk())
            .andExpect(content().json(
            """
                [
                    {
                        "id": "id1",
                        "number": "123",
                        "product": 
                            {
                                "id": "prod_id1",
                                "name": "Prod1",
                                "category": "FURNITURE",
                                "group": "SEATING",
                                "family": "CHAIR",
                                "features": 
                                        {
                                            "dimension": {
                                                "width": 
                                                    {
                                                        "number": 50,
                                                        "unit": "CM"
                                                    },
                                                "length": 
                                                    {
                                                        "number": 50,
                                                        "unit": "CM"
                                                    },
                                                "height": 
                                                    {
                                                        "number": 50,
                                                        "unit": "CM"
                                                    }
                                            },
                                            "weight":
                                                {
                                                    "number": 10,
                                                    "unit": "KG"
                                                },
                                            "colors":
                                                [
                                                    "OAK",
                                                    "BEECH",
                                                    "BLACK"
                                                ]
                                        },
                                "description": "Description",
                                "images":
                                    {
                                       "small": "http://image1.test",                   
                                       "medium": "http://image2.test",                    
                                       "large": "http://image3.test"                    
                                    }
                            },
                        "price": 123.45,
                        "currency": "EUR",
                        "amount": 100
                    }
                ]
            """
        ));
    }
}
