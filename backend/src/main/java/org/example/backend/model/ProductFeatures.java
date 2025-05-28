package org.example.backend.model;

import java.util.List;


public record ProductFeatures(   
    Dimension dimension,
    Measure weight,
    List<Color> colors
) {}