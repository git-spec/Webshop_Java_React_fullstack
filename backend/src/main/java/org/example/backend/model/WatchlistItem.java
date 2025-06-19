package org.example.backend.model;

public record WatchlistItem(
    String id,
    String userEmail,
    Product product
) {}
