package org.example.backend.model;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;



@Document("orders")
public record OrderCompleted(
    String d,
    List<Article> cart,
    Map<String, Object> paypal
) {

}
