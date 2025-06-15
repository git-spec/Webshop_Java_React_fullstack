package org.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createOrder_shouldReturn200_whenGetsMap() throws Exception {
        // GIVEN
        String requestBody = "{\"amount\": 10.00, \"currency\": \"USD\"}";
        // WHEN // THEN
        mockMvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "cart": [
                            {
                                "id": "123",
                                "quantity": 1
                            }
                        ]
                    }    
                """
            ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orderId").exists());
    }

    @Test
    void captureOrder_shouldReturn200_whenGetsID() throws Exception {
        // GIVEN
        String orderID = "123";
        // WHEN // THEN
        mockMvc.perform(post("/orders/{orderID}/capture", orderID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(orderID));
    }
    
    @Test
    void addOrder_shouldReturn200_whenGetsOrder() throws Exception {
        // WHEN // THEN
        mockMvc.perform(post("/api/orders/completed").contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "cart": [
                            {
                                "productID": "123",
                                "amount": 1,
                                "price": 10.00
                            }
                        ],
                        "paypal": {
                            "amount": 10.00,
                            "currency": "USD"
                        }
                    }    
                """
            )
        ).andExpect(status().isOk());
    }
    
    @Test
    void addOrder_shouldReturn400_whenGetsInvalidOrder() throws Exception {
        // WHEN // THEN
        mockMvc.perform(post("/api/orders/completed").contentType(MediaType.APPLICATION_JSON)
            .content(
                """    
                """
            )
        ).andExpect(status().isBadRequest());
    }
}
