package org.example.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.example.backend.repository.OrderRepo;
import org.example.backend.exception.BadRequestException;
import org.example.backend.model.Article;
import org.example.backend.model.dto.OrderCompletedDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class OrderServiceTest {
    OrderRepo mockRepo = Mockito.mock(OrderRepo.class);
    
    OrderService mockService = new OrderService(mockRepo);

    Map<String, Object> paypal = new HashMap<>();
    OrderCompletedDTO orderCompletedDTO;
    String BAD_REQUEST_MESSAGE_FORMAT = "Anfrage ist nicht korrekt.";


    @BeforeEach
    void setUp() {
        paypal.put("amount", 10.00);
        paypal.put("currency", "USD");

        orderCompletedDTO = new OrderCompletedDTO(
            List.of(new Article("123", "ash", 1, new BigDecimal("10.00"))),
            paypal
        );
    }

    @Test
    void addOrder_shouldReturn200_WhenIsCalled() throws BadRequestException {
        // GIVEN
        Mockito.when(mockRepo.save(orderCompletedDTO)).thenReturn(orderCompletedDTO);
        Mockito.when(mockService.addOrder(any(OrderCompletedDTO.class)))
            .thenReturn(ResponseEntity.ok(HttpStatus.OK));
        // WHEN
        ResponseEntity<HttpStatus> actual = mockService.addOrder(any(OrderCompletedDTO.class));
        // THEN
        assertEquals(ResponseEntity.ok().build(), actual);
        Mockito.verify(mockRepo, Mockito.times(1)).save(null);
    }

    @Test
    void addOrder_shouldReturn400_WhenGetInvalidOrder() {
        // GIVEN

        // WHEN
        Mockito.doThrow(new RuntimeException()).when(mockRepo).save(any());
        // THEN
        BadRequestException exception = assertThrows(BadRequestException.class, () -> mockService.addOrder(orderCompletedDTO));
        assertEquals(exception.getMessage(), BAD_REQUEST_MESSAGE_FORMAT);
        Mockito.verify(mockRepo, Mockito.times(1)).save(any());
    }
}
