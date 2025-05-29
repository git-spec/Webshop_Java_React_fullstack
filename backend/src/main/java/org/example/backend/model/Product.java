package org.example.backend.model;

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
    private final String description;
    private final Images images;
}
