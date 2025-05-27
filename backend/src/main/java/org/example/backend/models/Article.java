package org.example.backend.models;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
public class Article {
    private final String id;
    private final String number;
    private final Product product;
    private final BigDecimal price;
    private final Currency currency;
    private final int amount;
}
