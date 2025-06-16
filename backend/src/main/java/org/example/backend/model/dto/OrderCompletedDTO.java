package org.example.backend.model.dto;

import java.util.List;
import java.util.Map;

import org.example.backend.model.Article;
import org.springframework.data.mongodb.core.mapping.Document;


public record OrderCompletedDTO(
    List<Article> cart,
    Map<String, Object> paypal
) {

}
