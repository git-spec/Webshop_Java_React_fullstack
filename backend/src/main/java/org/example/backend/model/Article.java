package org.example.backend.model;

import java.math.BigDecimal;

public record Article(
    String productID,
    String productName,
    String color,
    Integer amount,
    Currency currency,
    BigDecimal price
) {}
