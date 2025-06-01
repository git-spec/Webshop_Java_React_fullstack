package org.example.backend.repository;

import org.example.backend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
    List<Product> findAllByCategory(String category);
    List<Product> findByCategoryAndGroup(String category, String group);
    List<Product> findAllByFamily(String family);
    boolean existsByCategory(String category);
    boolean existsByCategoryAndGroup(String category, String group);
}
