package org.example.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

import org.example.backend.model.Currency;
import org.example.backend.model.Product;


@Data
@AllArgsConstructor
public class ArticleDTO {
    private final String number;
    private final Product product;
    private final BigDecimal price;
    private final Currency currency;
    private final int amount;
}
