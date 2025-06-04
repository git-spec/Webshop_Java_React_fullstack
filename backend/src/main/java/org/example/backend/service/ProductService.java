package org.example.backend.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

import org.example.backend.model.Product;
import org.example.backend.model.Category;
import org.example.backend.model.Group;
import org.example.backend.model.Family;
import org.example.backend.repository.ProductRepo;
import org.example.backend.exception.NotFoundException;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    private static final String NOT_FOUND_MESSAGE_FORMAT = "Seite nicht gefunden.";

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    /**
     * Gets products by category.
     * @param category
     * @throws NotFoundException
     */
    public List<Product> getProductsByCategory(String category) throws NotFoundException {
        // Gets enum of string.
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            return productRepo.findAllByCategory(categoryEnum.toString());
        } catch(IllegalArgumentException e) {
            throw new NotFoundException(NOT_FOUND_MESSAGE_FORMAT, e);
        }
    }

    /**
     * Gets products by group.
     * @param category
     * @param group
     * @throws NotFoundException 
     */
    public List<Product> getProductsByCategoryAndGroup(String category, String group) throws NotFoundException {
        // Gets enum of string.
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            Group groupEnum = Group.valueOf(group.toUpperCase());
            return productRepo.findAllByCategoryAndGroup(categoryEnum.toString(), groupEnum.toString());
        } catch(IllegalArgumentException e) {
            throw new NotFoundException(NOT_FOUND_MESSAGE_FORMAT, e);
        }
    }

    /**
     * Gets products by family.
     * @param category
     * @param group
     * @param family
     * @throws NotFoundException 
     */
    public List<Product> getProductsByCategoryAndGroupAndFamily(
        String category, 
        String group, 
        String family
    ) throws NotFoundException {
        // Gets enum of string.
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            Group groupEnum = Group.valueOf(group.toUpperCase());
            Family familyEnum = Family.valueOf(family.toUpperCase());
            return productRepo.findAllByCategoryAndGroupAndFamily(
                categoryEnum.toString(), 
                groupEnum.toString(), 
                familyEnum.toString()
            );
        } catch(IllegalArgumentException e) {
            throw new NotFoundException(NOT_FOUND_MESSAGE_FORMAT, e);
        }
    }

    public Optional<Product> getProductByID(String id) {
        return productRepo.findById(id);
    }
}
