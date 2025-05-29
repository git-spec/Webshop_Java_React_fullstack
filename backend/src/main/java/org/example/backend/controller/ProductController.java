package org.example.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import org.example.backend.service.ProductService;
import org.example.backend.model.Article;
import org.example.backend.model.Category;


@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/articles")
    public List<Article> getArticles() {
        return productService.getArticles();
    }

    @GetMapping("/articles/{category}")
    public List<Article> getArticlesByCategory(@PathVariable String category) {
        return productService.getArticlesByCategory(category);
    }
}
