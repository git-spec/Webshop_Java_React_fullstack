package org.example.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.example.backend.models.Group;
import org.example.backend.models.Family;
import org.example.backend.models.Category;
import org.example.backend.models.ProductFeatures;


@Data
@AllArgsConstructor
public class ProductDTO {      
    private final String number;
    private final String name;
    private final Category category;
    private final Group group;
    private final Family family;
    private final ProductFeatures features;
    private final String description;
}
