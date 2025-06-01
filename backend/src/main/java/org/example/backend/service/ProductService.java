package org.example.backend.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.example.backend.model.Product;
import org.example.backend.model.Category;
import org.example.backend.model.Group;
import org.example.backend.repository.ProductRepo;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    /**
     * Gets products by category.
     * @param category
     * @throws IllegalArgumentException 
     */
    public List<Product> getProductsByCategory(String category) {
        // Gets enum of string.
        Category categoryEnum = Category.valueOf(category.toUpperCase());
        return productRepo.findAllByCategory(categoryEnum.toString());
    }

    /**
     * Gets products by group.
     * @param group
     * @throws IllegalArgumentException 
     */
    public List<Product> getProductsByCategoryAndGroup(String category, String group) {
        // Gets enum of string.
        Category categoryEnum = Category.valueOf(category.toUpperCase());
        Group groupEnum = Group.valueOf(group.toUpperCase());
        return productRepo.findByCategoryAndGroup(categoryEnum.toString(), groupEnum.toString());
    }
}
