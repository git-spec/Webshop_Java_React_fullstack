package org.example.backend.repository;

import org.example.backend.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ArticleRepo extends MongoRepository<Article, String> {
    List<Article> findAllByProductCategory(String category);
    List<Article> findAllByProductGroup(String group);
    List<Article> findAllByProductFamily(String family);
}
