package org.example.backend.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;


@Document("users")
public record User(
    String id, 
    String email, 
    String firstname, 
    String lastname,
    List<String> watchlist
) {}
