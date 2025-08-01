package org.example.backend.controller;

import java.util.List;
import java.util.Optional;

import org.example.backend.exception.NotFoundException;
import org.example.backend.model.Product;
import org.example.backend.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

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
    public List<Product> getProductsByCategoryAndGroup(
        @PathVariable String category, 
        @PathVariable String group
    ) throws NotFoundException {
        return productService.getProductsByCategoryAndGroup(category, group);
    }

    @GetMapping("/products/{category}/{group}/{family}")
    public List<Product> getProductsByCategoryAndGroupAndFamily(
        @PathVariable String category, 
        @PathVariable String group, 
        @PathVariable String family
    ) throws NotFoundException {
        return productService.getProductsByCategoryAndGroupAndFamily(category, group, family);
    }

    @PostMapping("/products")
    public List<Product> getProductsByID(@RequestBody List<String> ids) {
        return productService.getProductsByID(ids);
    }

    @GetMapping("/product/{id}")
    public Optional<Product> getProductByID(@PathVariable String id) {
        return productService.getProductByID(id);
    }
}
