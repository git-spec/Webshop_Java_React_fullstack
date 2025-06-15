package org.example.backend.model;

import java.math.BigDecimal;

public record Article(
    String productID,
    String color,
    Integer amount,
    BigDecimal price
) {}
