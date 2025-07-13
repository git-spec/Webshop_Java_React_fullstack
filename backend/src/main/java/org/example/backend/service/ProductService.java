package org.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.backend.Utils;
import org.example.backend.exception.IllegalArgumentException;
import org.example.backend.exception.AccessException;
import org.example.backend.exception.NotFoundException;
import org.example.backend.model.Category;
import org.example.backend.model.Family;
import org.example.backend.model.Group;
import org.example.backend.model.Product;
import org.example.backend.repository.ProductRepo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    static final String NOT_FOUND_MESSAGE_FORMAT = "Seite nicht gefunden.";
    static final String ILLEGAL_ARGUMENT = "Angaben sind nicht korrekt oder fehlen.";

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

    public List<Product> getProductsByID(List<String> ids) throws IllegalArgumentException, AccessException {
        List<Boolean> validation = ids.stream().map(id -> Utils.isValidUuidFormat(id)).toList();
        if (!validation.contains(false)) {
            return productRepo.findByIdIn(ids).orElseThrow(() -> new AccessException(NOT_FOUND_MESSAGE_FORMAT));
        } else {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
    }
}
