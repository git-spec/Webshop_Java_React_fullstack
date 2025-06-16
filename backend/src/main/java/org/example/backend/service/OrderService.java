package org.example.backend.service;

import org.example.backend.exception.BadRequestException;
import org.example.backend.model.dto.OrderCompletedDTO;
import org.example.backend.repository.OrderRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    private static final String BAD_REQUEST_MESSAGE_FORMAT = "Anfrage ist nicht korrekt.";

    /**
     * Adds order to db.
     * @param order
     * @throws BadRequestException 
     */
    public ResponseEntity<HttpStatus> addOrder(OrderCompletedDTO order) throws BadRequestException {
        try {
            orderRepo.save(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequestException(BAD_REQUEST_MESSAGE_FORMAT, e);
        }
    }
}
