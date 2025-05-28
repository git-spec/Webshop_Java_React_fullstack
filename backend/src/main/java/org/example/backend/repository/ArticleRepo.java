package org.example.backend.repository;

import org.example.backend.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface ArticleRepo extends MongoRepository<Article, String> {
    List<Article> findAllByProductCategory(String category);
    List<Article> findAllByProductGroup(String group);
    List<Article> findAllByProductFamily(String family);
}
