package org.example.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.example.backend.repository.OrderRepo;
import org.example.backend.exception.BadRequestException;
import org.example.backend.exception.IllegalArgumentException;
import org.example.backend.exception.EntriesNotFoundException;
import org.example.backend.model.Article;
import org.example.backend.model.OrderCompleted;
import org.example.backend.model.dto.OrderCompletedDTO;
import org.example.backend.model.PayPal;

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
    List<Article> cart = List.of(new Article("123", "ash", 1, new BigDecimal("10.00")));
    PayPal paypal = PayPal.builder()
        .id("1")
        .email(email)
        .firstname("Jon")
        .lastname("Doe")
        .build();
    Map<String, Object> paypalDTO = new HashMap<>();
    OrderCompleted orderCompleted = new OrderCompleted("1", cart, paypal);
    OrderCompletedDTO orderCompletedDTO = new OrderCompletedDTO(cart, paypalDTO);
    String BAD_REQUEST_MESSAGE_FORMAT = "Anfrage ist nicht korrekt.";

    @Test
    void getOrdersByEmail_shouldReturnOrders_whenGetEmail() throws IllegalArgumentException, EntriesNotFoundException {
        // GIVEN
        List<OrderCompleted> expected = List.of(orderCompleted);
        mockRepo.save(orderCompleted);
        // WHEN
        Mockito.when(mockRepo.findAllByPaypalEmail(email)).thenReturn(expected);
        List<OrderCompleted> actual = orderService.getOrdersByEmail(email);
        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void getOrdersByEmail_shouldThrowNotFound_whenNoEntryForEmail() throws IllegalArgumentException, EntriesNotFoundException {
        // WHEN
        try {
            orderService.getOrdersByEmail("fehlerhaft");
        } catch(IllegalArgumentException e) {
            // THEN
            assertTrue(true);
        }
    }

    @Test
    void getOrdersByEmail_shouldThrowBadRequest_whenGetInvalidEmail() throws IllegalArgumentException, EntriesNotFoundException {
        // WHEN
        Mockito.when(mockRepo.findAllByPaypalEmail(email)).thenReturn(List.of());
        try {
            orderService.getOrdersByEmail(email);
        } catch(EntriesNotFoundException e) {
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
        BadRequestException exception = assertThrows(BadRequestException.class, () -> orderService.addOrder(orderCompletedDTO));
        // THEN
        assertEquals(exception.getMessage(), BAD_REQUEST_MESSAGE_FORMAT);
    }
}
