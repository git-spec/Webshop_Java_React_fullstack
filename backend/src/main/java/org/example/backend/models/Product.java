package org.example.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.example.backend.models.dto.ProductDTO;


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
}
