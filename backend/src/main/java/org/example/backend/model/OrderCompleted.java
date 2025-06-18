package org.example.backend.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;



@Document("orders")
public record OrderCompleted(
    String id,
    List<Article> cart,
    PayPal paypal
) {

}
