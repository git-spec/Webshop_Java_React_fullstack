package org.example.backend.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.example.backend.model.Product;
import org.example.backend.model.Category;
import org.example.backend.repository.ProductRepo;
import org.example.backend.exception.CategoryNotFoundException;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo articleRepo;

    private static final String CATEGORY_NOT_FOUND_MESSAGE_FORMAT = "Kategorie %s existiert nicht.";

    public List<Product> getProducts() {
        return articleRepo.findAll();
    }

    /**
     * Gets articles by category.
     * @param category
     */
    public List<Product> getProductsByCategory(String category) {
        // Gets enum of string.
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            return articleRepo.findAllByCategory(categoryEnum.toString());
        } catch (Exception e) {
            throw new CategoryNotFoundException(CATEGORY_NOT_FOUND_MESSAGE_FORMAT, category);
        }
    }
}
