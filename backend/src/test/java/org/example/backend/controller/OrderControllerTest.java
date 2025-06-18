package org.example.backend.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.math.BigDecimal;

import org.example.backend.exception.EntriesNotFoundException;
import org.example.backend.model.Article;
import org.example.backend.model.OrderCompleted;
import org.example.backend.model.PayPal;
import org.example.backend.repository.OrderRepo;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getOrdersByEmail_shouldReturnOrders_whenGetEmail() throws Exception {
        // GIVEN
        String email = "jon@doe.io";
        List<Article> articles = List.of(new Article("123", "BLACK", 1, new BigDecimal("10.00")));
        PayPal paypal = PayPal.builder().id("123").email(email).firstname("Jon").lastname("Doe").build();
        OrderCompleted order = new OrderCompleted("123", articles, paypal);
        orderRepo.save(order);
        // WHEN // THEN
        mockMvc.perform(get("/api/orders/{email}", email))
            .andExpect(status().isOk())
            .andExpect(content().json(String.valueOf(objectMapper.writeValueAsString(List.of(order)))));
    }

    @Test
    void getOrdersByEmail_shouldThrowBadRequest_whenGetInvalidEmail() throws Exception {
        // GIVEN
        String BAD_REQUEST_MESSAGE_FORMAT = "Anfrage ist nicht korrekt.";
        // WHEN // THEN
        mockMvc.perform(get("/api/orders/{email}", "123"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertInstanceOf(IllegalArgumentException.class, result.getResolvedException()))
            .andExpect(result -> assertEquals(BAD_REQUEST_MESSAGE_FORMAT, result.getResolvedException().getMessage()));
    }

    @Test
    void getOrdersByEmail_shouldThrowNotFound_whenNoEntryForEmail() throws Exception {
        // GIVEN
        String email = "jon@doe.ioe.io";
        String ORDERS_NOT_FOUND_MESSAGE_FORMAT = "Bestellungen wurden nicht gefunden.";
        // WHEN // THEN
        mockMvc.perform(get("/api/orders/{email}", email))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertInstanceOf(EntriesNotFoundException.class, result.getResolvedException()))
            .andExpect(result -> assertEquals(ORDERS_NOT_FOUND_MESSAGE_FORMAT, result.getResolvedException().getMessage()));
    }

    // @Test
    // void createOrder_shouldReturn200_whenGetsMap() throws Exception {
    //     // GIVEN
    //     String requestBody = "{\"amount\": 10.00, \"currency\": \"USD\"}";
    //     // WHEN // THEN
    //     mockMvc.perform(post("/api/order").contentType(MediaType.APPLICATION_JSON)
    //         .content(
    //             """
    //                 {
    //                     "cart": [
    //                         {
    //                             "id": "123",
    //                             "quantity": 1
    //                         }
    //                     ]
    //                 }    
    //             """
    //         ))
    //         .andExpect(status().isOk())
    //         .andExpect(jsonPath("$.orderId").exists());
    // }

    // @Test
    // void captureOrder_shouldReturn200_whenGetsID() throws Exception {
    //     // GIVEN
    //     String orderID = "123";
    //     // WHEN // THEN
    //     mockMvc.perform(post("/order/{orderID}/capture", orderID))
    //     .andExpect(status().isOk())
    //     .andExpect(jsonPath("$.id").value(orderID));
    // }
    
    @Test
    void addOrder_shouldReturn200_whenGetsOrder() throws Exception {
        // WHEN // THEN
        mockMvc.perform(post("/api/order/completed").contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "id": "123",
                        "cart": [
                            {
                                "productID": "123",
                                "color": "BLACK",
                                "amount": 1,
                                "price": "10.00"
                            }
                        ],
                        "paypal": {
                            "id": "123",
                            "email": "jon@doe.ioe.io",
                            "firstname": "Jon",
                            "lastname": "Doe"
                        }
                    }    
                """
            )
        ).andExpect(status().isOk());
    }
    
    @Test
    void addOrder_shouldReturn400_whenGetsInvalidOrder() throws Exception {
        // WHEN // THEN
        mockMvc.perform(post("/api/order/completed").contentType(MediaType.APPLICATION_JSON)
            .content(
                """    
                """
            )
        ).andExpect(status().isBadRequest());
    }
}
