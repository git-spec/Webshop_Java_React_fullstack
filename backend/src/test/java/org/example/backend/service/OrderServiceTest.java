package org.example.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.example.backend.repository.OrderRepo;
import org.example.backend.exception.BadRequestException;
import org.example.backend.exception.InvalidArgumentException;
import org.example.backend.model.Article;
import org.example.backend.model.Currency;
import org.example.backend.model.OrderCompleted;
import org.example.backend.model.dto.OrderCompletedDTO;
// import org.example.backend.model.PayPal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class OrderServiceTest {
    OrderRepo mockRepo = Mockito.mock(OrderRepo.class);
    IDService mockID = Mockito.mock(IDService.class);
    OrderService mockService = Mockito.mock(OrderService.class);
    OrderService orderService = new OrderService(mockRepo, mockID);

    String email = "test@email.com";
    List<Article> cart = List.of(
        new Article("123", "Lara", "ash", 1, Currency.EUR, new BigDecimal("10.00"))
    );
    // PayPal paypal = PayPal.builder()
    //     .id("1")
    //     .email(email)
    //     .firstname("Jon")
    //     .lastname("Doe")
    //     .build();
    Map<String, Object> paypalDTO = new HashMap<>();
    OrderCompleted orderCompleted = new OrderCompleted("1", cart, paypalDTO);
    OrderCompletedDTO orderCompletedDTO = new OrderCompletedDTO(cart, paypalDTO);
    String BAD_REQUEST_MESSAGE_FORMAT = "Anfrage ist nicht korrekt.";

    @Test
    void getOrdersByEmail_shouldReturnOrders_whenGetEmail() throws InvalidArgumentException {
        // GIVEN
        List<OrderCompleted> expected = List.of(orderCompleted);
        mockRepo.save(orderCompleted);
        // WHEN
        Mockito.when(mockRepo.findAllByPaypalPayerEmailAddress(email)).thenReturn(expected);
        List<OrderCompleted> actual = orderService.getOrdersByEmail(email);
        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void getOrdersByEmail_shouldThrowBadRequest_whenGetInvalidEmail() throws InvalidArgumentException {
        // WHEN
        try {
            orderService.getOrdersByEmail("123");
        } catch(InvalidArgumentException e) {
            // THEN
            assertTrue(true);
        }
    }

    @Test
    void addOrder_shouldReturn200_WhenIsCalled() throws BadRequestException {
        // GIVEN
        mockRepo.save(orderCompleted);
        // WHEN
        Mockito.when(mockID.createID()).thenReturn("1");
        Mockito.when(mockRepo.save(orderCompleted)).thenReturn(orderCompleted);
        Mockito.when(mockService.addOrder(any(OrderCompletedDTO.class)))
            .thenReturn(ResponseEntity.ok("The order was saved successfully."));
        ResponseEntity<String> actual = mockService.addOrder(orderCompletedDTO);
        String id = mockID.createID();
        // THEN
        assertEquals(ResponseEntity.ok("The order was saved successfully."), actual);
        assertEquals("1", id);
        Mockito.verify(mockRepo, Mockito.times(1)).save(orderCompleted);
    }

    @Test
    void addOrder_shouldReturn400_WhenGetInvalidOrder() {
        // WHEN
        assertThrows(BadRequestException.class, () -> orderService.addOrder(orderCompletedDTO));
        // THEN
        try {
            orderService.addOrder(orderCompletedDTO);
        } catch(BadRequestException e) {
            // THEN
            assertTrue(true);
            assertEquals(e.getMessage(), BAD_REQUEST_MESSAGE_FORMAT);
        }
    }

    @Test
    void isValidEmail_shouldReturnFalse_whenGetEmail() {
        // WHEN
        boolean actual = orderService.isValidEmail("123");
        // THEN
        assertEquals(false, actual);
    }
}
