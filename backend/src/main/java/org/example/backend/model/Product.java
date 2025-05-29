package org.example.backend.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class Product {
    private final String id;      
    private final String number;
    private final String name;
    private final Category category;
    private final Group group;
    private final Family family;
    private final ProductFeatures features;
    private final String info;
    private final String description;
    private final Images images;
    private final BigDecimal price;
    private final Currency currency;
    private final int amount;

}
