package org.example.backend.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.example.backend.exception.BadRequestException;
import org.example.backend.exception.InvalidArgumentException;
import org.example.backend.model.OrderCompleted;
// import org.example.backend.model.PayPal;
import org.example.backend.model.dto.OrderCompletedDTO;
import org.example.backend.repository.OrderRepo;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final IDService idService;
    

    private static final String BAD_REQUEST_MESSAGE_FORMAT = "Anfrage ist nicht korrekt.";
    private static final String ORDERS_NOT_FOUND_MESSAGE_FORMAT = "Bestellungen wurden nicht gefunden.";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Adds order to db.
     * @param order
     * @throws BadRequestException 
     */
    public ResponseEntity<String> addOrder(OrderCompletedDTO orderDTO) {
        try {
            // String id = orderDTO.paypal().get("id").toString();
            // String email = null;
            // String firstname = null;
            // String lastname = null;
            // Object payer = orderDTO.paypal().get("payer");
            // if (id == null) {
            //     throw new InvalidArgumentException("PayPal id is missing.");
            // }
            // if (payer instanceof Map) {
            //     @SuppressWarnings("unchecked")
            //     Map<String, Object> payerMap = (Map<String, Object>) payer;
            //     Object emailObject = payerMap.get("email_address");
            //     if (emailObject != null) {
            //         email = emailObject.toString();
            //         // ... weiterverarbeiten
            //     }
            //     @SuppressWarnings("unchecked")
            //     Map<String, String> nameMap = (Map<String, String>) payerMap.get("name");
            //     if (nameMap != null) {
            //         firstname = nameMap.get("given_name");
            //         lastname = nameMap.get("surname");
            //     }
            // }
            // if (
            //     email != null && !email.isEmpty() 
            //     && firstname != null&& !firstname.isEmpty() 
            //     && lastname != null && !lastname.isEmpty()
            // ) {
            //     PayPal paypal = PayPal.builder()
            //         .id(id)
            //         .email(email)
            //         .firstname(firstname)
            //         .lastname(lastname)
            //         .build();
            //     OrderCompleted order = new OrderCompleted(
            //         idService.createID(),
            //         orderDTO.cart(),
            //         paypal
            //     );
            //     orderRepo.save(order);
            // }
            if (orderDTO.paypal().isEmpty()) {
                throw new BadRequestException(BAD_REQUEST_MESSAGE_FORMAT);
            } else {
                OrderCompleted order = new OrderCompleted(idService.createID(), orderDTO.cart(), orderDTO.paypal());
                orderRepo.save(order);
                return ResponseEntity.ok("The order was saved successfully.");
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(BAD_REQUEST_MESSAGE_FORMAT);
        }
    }

    public List<OrderCompleted> getOrdersByEmail(String email) throws InvalidArgumentException {
        boolean valid = isValidEmail(email);
        if (!valid) {
            throw new InvalidArgumentException(BAD_REQUEST_MESSAGE_FORMAT);
        } else {
            return orderRepo.findAllByPaypalPayerEmailAddress(email);
        }
    }
}
