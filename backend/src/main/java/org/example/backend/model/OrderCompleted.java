package org.example.backend.model;

import java.util.List;
import java.util.Map;


public record OrderCompleted(
    List<Article> cart,
    Map<String, Object> paypal
) {

}
