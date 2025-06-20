package org.example.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;


@Document("watchlists")
public record WatchlistItem(
    String id,
    String userEmail,
    Product product
) {}
