package org.example.backend.model.dto;

import org.example.backend.model.Product;

public record WatchlistItemDTO(
    String userEmail,
    Product product
) {}
