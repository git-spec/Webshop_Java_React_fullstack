package org.example.backend.repository;

import org.example.backend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
    List<Product> findAllByCategory(String category);
    List<Product> findAllByCategoryAndGroup(String category, String group);
    List<Product> findAllByCategoryAndGroupAndFamily(String category, String group, String family);
}
