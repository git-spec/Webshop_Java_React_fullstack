package org.example.backend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.example.backend.exception.NotFoundException;
import org.example.backend.model.Product;
import org.example.backend.model.dto.ErrorDTO;
import org.example.backend.service.ProductService;


@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    private static final String NOT_FOUND_MESSAGE_FORMAT = "Seite nicht gefunden.";

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/products/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) throws NotFoundException {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/products/{category}/{group}")
    public List<Product> getProductsByCategoryAndGroup(@PathVariable String category, @PathVariable String group) throws NotFoundException {
        return productService.getProductsByCategoryAndGroup(category, group);
    }

    /**
     * Throws NOT_FOUND Exception if path variable is invalid.
     * @param IllegalArgumentException
     * @throws NotFoundException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return new ResponseEntity<>(
                ErrorDTO.fromException(new NotFoundException(NOT_FOUND_MESSAGE_FORMAT, exception)),
                HttpStatus.NOT_FOUND
            );
    }
}
