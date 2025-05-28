package org.example.backend.model.dto;

import org.example.backend.model.Category;
import org.example.backend.model.Family;
import org.example.backend.model.Group;
import org.example.backend.model.ProductFeatures;

import lombok.AllArgsConstructor;
import lombok.Data;


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
