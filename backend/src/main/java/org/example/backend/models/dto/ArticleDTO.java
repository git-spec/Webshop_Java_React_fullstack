package org.example.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

import org.example.backend.models.Currency;
import org.example.backend.models.Product;


@Data
@AllArgsConstructor
public class ArticleDTO {
    private final String number;
    private final Product product;
    private final BigDecimal price;
    private final Currency currency;
    private final int amount;
}
