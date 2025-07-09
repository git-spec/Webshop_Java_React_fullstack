package org.example.backend.controller;

import java.awt.PageAttributes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.example.backend.exception.AccessException;
import org.example.backend.exception.DuplicateException;
import org.example.backend.exception.IllegalArgumentException;
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
import org.example.backend.model.User;
import org.example.backend.model.WatchlistItem;
import org.example.backend.model.dto.UserDTO;
import org.example.backend.repository.UserRepo;
import org.example.backend.repository.WatchlistRepo;
import org.example.backend.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private  UserRepo userRepo;
    @MockitoBean
    private  UserService userService;
    @Autowired
    private  WatchlistRepo watchlistRepo;
    @Autowired
    private ObjectMapper objectMapper;

    UpdateResult updateResult = mock(UpdateResult.class);
    private final Measure width = new Measure(
                                    49.0,
                                    Unit.CM
                                );
    private final Measure length = new Measure(
                                    45.0,
                                    Unit.CM
                                );
    private final Measure height = new Measure(
                                    78.0,
                                    Unit.CM
                                );
    private final ProductFeatures feat1 = new ProductFeatures(
                        new Dimension(width, length, height),
                        new Measure(6.0, Unit.KG),
                        List.of(Material.OAK, Material.ASH),
                        List.of("OAK", "ASH", "ASH_BLACK")
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
                        List.of("/public/medium/lara/Lara--1990--chair--cutoutAngle-2--Ash--CM.jpg"),
                        List.of("/public/large/lara/ercol-lara-chair-natural-ash_900x.jpg")
                    )
                )
                .price(BigDecimal.valueOf(370))
                .currency(Currency.GBP)
                .amount(1)
                .build();
    private final String id = "123";
    private final String email = "jon@doe.io";
    private final WatchlistItem item = new WatchlistItem(id, email, product);
    private User user = new User("test123", "123@test.com", "Jon", "Doe", Instant.now());
    private UserDTO userDTO = new UserDTO("123@test.com", "Jon", "Doe");

    @Test
    void getWatchlistItems_shouldReturnItems_whenGetEmail() throws Exception {
        // GIVEN
        watchlistRepo.save(item);
        List<WatchlistItem> expected = Collections.singletonList(item);
        // WHEN // THEN
        mockMvc.perform(get("/api/user/watchlist/{email}", email))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    // @Test
    // void addWatchlistItem_shouldReturn200_whenGetItem() throws Exception {
    //     mockMvc.perform(post("/api/user/watchlist")
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .content(
    //             """
    //                 {
    //                     "userEmail": "jon@doe.io",
    //                     "product": {
    //                         "id": "1536716",
    //                         "number": "1990",
    //                         "name": "LARA Chair",
    //                         "manufacturer": "Erol",
    //                         "category": "FURNITURE",
    //                         "group": "SEATING",
    //                         "family": "CHAIR",
    //                         "features": {
    //                             "dimension": {
    //                                 "width": {
    //                                     "number": 49.0,
    //                                     "unit": "CM"
    //                                 },
    //                                 "height": {
    //                                     "number": 78.0,
    //                                     "unit": "CM"
    //                                 },
    //                                 "length": {
    //                                     "number": 46.0,
    //                                     "unit": "CM"
    //                                 }
    //                             },
    //                             "weight": {
    //                                 "number": 6.0,
    //                                 "unit": "KG"
    //                             },
    //                             "materials": [
    //                                 "OAK",
    //                                 "ASH"
    //                             ],
    //                             "colors": [
    //                                 "OAK", 
    //                                 "ASH", 
    //                                 "ASH_BLACK"
    //                             ]
    //                         },
    //                         "info": "Info",
    //                         "description": "Description",
    //                         "images": {
    //                             "small": ["/public/small/lara/Lara--1990--chair--cutoutAngle-2--Ash--CM.jpg"],
    //                             "medium": ["/public/medium/lara/Lara--1990--chair--cutoutAngle-2--Ash--CM.jpg"],
    //                             "large": ["/public/large/lara/ercol-lara-chair-natural-ash_900x.jpg"]
    //                         },
    //                         "price": 370,
    //                         "currency": "GBP",
    //                         "amount": 1
    //                     }
    //                 }     
    //             """
    //         ))
    //         .andExpect(status().isOk())
    //         .andExpect(jsonPath("$.id").isNotEmpty())
    //         .andExpect(jsonPath("$.userEmail").value(email));
    // }

    // @Test
    // void deleteWatchlistItem_shouldDeleteItem_whenGetID() throws Exception {
    //     // WHEN // THEN
    //     mockMvc.perform(delete("/api/watchlist/{id}", id))
    //         .andExpect(status().isOk());
    // }

    @Test
    void addUser_shouldReturnUser_whenGetUser() throws Exception {
        // GIVEN
        userRepo.save(user);
        String jsonDTO = objectMapper.writeValueAsString(userDTO);
        // WHEN
        when(userService.addUser(userDTO)).thenReturn(user);
        // THEN
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO))
            .andExpect(status().isOk());
    }

    @Test
    void addUser_shouldThrow409_whenUserExist() throws Exception {
        // GIVEN
        String jsonDTO = objectMapper.writeValueAsString(userDTO);
        // WHEN
        when(userService.addUser(userDTO))
            .thenThrow(new DuplicateException("Fehler"));
        // THEN
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO))
            .andExpect(status().isConflict());
    }

    @Test
    void addUser_shouldThrow400_whenGetNull() throws Exception {
        // WHEN
        when(userService.addUser(any(UserDTO.class)))
            .thenThrow(new IllegalArgumentException("Fehler"));
        // THEN
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """      
                """
            ))
            .andExpect(status().isBadRequest());
    }

    @Test
    void addUser_shouldThrow500_whenUpdateFailed() throws Exception {
        // GIVEN
        String jsonDTO = objectMapper.writeValueAsString(userDTO);
        // WHEN 
        when(userService.addUser(userDTO))
            .thenThrow(new AccessException("Fehler"));
        // THEN
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO)).andExpect(status().isInternalServerError());
    }

    @Test
    void updateWatchlist_shouldAddProductToWatchlist_whenGetIdTokenAndProductId() throws Exception {
        // GIVEN
        UpdateResult result = UpdateResult.acknowledged(1L, 1L, null);
        user.setWatchlist(List.of());
        userRepo.save(user);
        // WHEN 
        when(userService.updateWatchlist("test123", "prod123")).thenReturn(result); 
        // THEN
        mockMvc.perform(put("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "idToken": "test123",
                        "productID": "prod123"
                    }        
                """
            )).andExpect(status().isOk());
        verify(userRepo).save(argThat(el ->
            el.getId().equals("test123") &&
            el.getEmail().equals("123@test.com") &&
            el.getGivenName().equals("Jon") &&
            el.getFamilyName().equals("Doe") &&
            el.getWatchlist().isEmpty()
        ));

    }

    @Test
    void updateWatchlist_shouldThrow500_whenUpdateFailed() throws Exception {
        // WHEN 
        when(updateResult.wasAcknowledged()).thenReturn(false);
        when(userService.updateWatchlist(anyString(), anyString()))
            .thenThrow(new AccessException("Fehler"));
        // THEN
        mockMvc.perform(put("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "idToken": "test123",
                        "productID": "prod123"
                    }        
                """
            )).andExpect(status().isInternalServerError());
    }

    @Test
    void updateWatchlist_shouldThrow409_whenProductExist() throws Exception {
        // GIVEN
        user.setWatchlist(List.of("prod123"));
        userRepo.save(user);
        // WHEN
        when(userService.updateWatchlist("test123", "prod123"))
            .thenThrow(new DuplicateException("Fehler"));
        // THEN
        mockMvc.perform(put("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "idToken": "test123",
                        "productID": "prod123"
                    }        
                """
            )).andExpect(status().isConflict());
    }

    @Test
    void updateWatchlist_shouldThrow400_whenGetNull() throws Exception {
        // WHEN 
        when(userService.updateWatchlist(null, null))
            .thenThrow(new IllegalArgumentException("Fehler"));
        // THEN
        mockMvc.perform(put("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "idToken": null,
                        "productID": null
                    }        
                """
            )).andExpect(status().isBadRequest());
    }
}
