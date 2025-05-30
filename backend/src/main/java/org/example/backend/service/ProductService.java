package org.example.backend.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.example.backend.model.Product;
import org.example.backend.model.Category;
import org.example.backend.model.Group;
import org.example.backend.repository.ProductRepo;
import org.example.backend.exception.CategoryNotFoundException;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    private static final String CATEGORY_NOT_FOUND_MESSAGE_FORMAT = "Kategorie %s existiert nicht.";
    private static final String GROUP_NOT_FOUND_MESSAGE_FORMAT = "KGruppe %s existiert nicht.";

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    /**
     * Gets products by category.
     * @param category
     */
    public List<Product> getProductsByCategory(String category) {
        // Gets enum of string.
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            return productRepo.findAllByCategory(categoryEnum.toString());
        } catch (Exception e) {
            throw new CategoryNotFoundException(CATEGORY_NOT_FOUND_MESSAGE_FORMAT, category);
        }
    }

    /**
     * Gets products by group.
     * @param group
     */
    public List<Product> getProductsByGroup(String group) {
        // Gets enum of string.
        try {
            Group groupEnum = Group.valueOf(group.toUpperCase());
            return productRepo.findAllByGroup(groupEnum.toString());
        } catch (Exception e) {
            throw new CategoryNotFoundException(GROUP_NOT_FOUND_MESSAGE_FORMAT, group);
        }
    }
}
