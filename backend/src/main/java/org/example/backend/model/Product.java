package org.example.backend.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Document("products")
@Builder
@Data
@AllArgsConstructor
public class Product {
    @Id
    private final String id;      
    private final String number;
    private final String name;
    private final String manufacturer;
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
