package org.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

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
import org.example.backend.model.WatchlistItem;
import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.repository.WatchlistRepo;
import org.example.backend.service.WatchlistService;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WatchlistControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private  WatchlistRepo watchlistRepo;
    @Autowired
    private ObjectMapper objectMapper;

    private final Measure width = new Measure(
                                    49,
                                    Unit.CM
                                );
    private final Measure length = new Measure(
                                    45,
                                    Unit.CM
                                );
    private final Measure height = new Measure(
                                    78,
                                    Unit.CM
                                );
    private final ProductFeatures feat1 = new ProductFeatures(
                        new Dimension(width, length, height),
                        new Measure(6, Unit.KG),
                        List.of(Material.OAK, Material.ASH),
                        List.of("oak", "ash", "black")
                    );          
    private final Product product = 
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
                .amount(1)
                .build();
    private final WatchlistItemDTO itemDTO = new WatchlistItemDTO("jon@doe.io", product);
    private final WatchlistItem item = new WatchlistItem("123", "jon@doe.io", product);
    private static final String SAVED_ITEM_SUCCESSFULLY = "Item was saved successfully.";

    @Test
    void addWatchlistItem_shouldReturn200_whenGetItem() throws Exception {
        mockMvc.perform(post("/api/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "userEmail": "jon@doe.io",
                        "product": {
                            "id": "1536716",
                            "number": "1990",
                            "name": "Lara Chair",
                            "manufacturer": "Erol",
                            "category": "FURNITURE",
                            "group": "SEATING",
                            "family": "CHAIR",
                            "features": {
                                "dimension": {
                                    "width": {
                                        "number": 49,
                                        "unit": "CM"
                                    },
                                    "length": {
                                        "number": 45,
                                        "unit": "CM"
                                    },
                                    "height": {
                                        "number": 78,
                                        "unit": "CM"
                                    }
                                },
                                "weight": {
                                    "number": 6,
                                    "unit": "KG"
                                },
                                "materials": {
                                    "number": 6,
                                    "unit": "KG"
                                },
                                "colors": [
                                    "oak", 
                                    "ash", 
                                    "black"
                                ]
                            },
                            "images": {
                                "small": ["/public/small/lara/Lara--1990--chair--cutoutAngle-2--Ash--CM.jpg"],
                                "medium": ["http://imag)e2.test"],
                                "large": ["/public/large/lara/Lara--1990--chair--cutoutAngle-2--Ash--)M.jpg"]
                            },
                            "price": "370",
                            "currency": "GBP",
                            "amount": 1
                        }
                    }     
                """
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(SAVED_ITEM_SUCCESSFULLY));
    }

    // // @Test
    // // void addWatchlistItem_shouldThrowBadRequest_whenGetInvalidEmail() throws Exception {
    // //     // GIVEN
    // //     String BAD_REQUEST_MESSAGE_FORMAT = "Anfrage ist nicht korrekt.";
    // //     // WHEN // THEN
    // //     mockMvc.perform(post("/api/watchlist").contentType(MediaType.APPLICATION_JSON)
    // //         .content(
    // //             """ 
    // //                 fehlerhaftaft   
    // //             """
    // //         )
    // //     ).andExpect(status().isBadRequest())
    // //         .andExpect(result -> assertInstanceOf(IllegalArgumentException.class, result.getResolvedException()))
    // //         .andExpect(result -> assertEquals(BAD_REQUEST_MESSAGE_FORMAT, result.getResolvedException().getMessage()));
    // // }
}
