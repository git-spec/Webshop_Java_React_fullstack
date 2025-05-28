package org.example.backend.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.example.backend.model.Article;
import org.example.backend.repository.ArticleRepo;


@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepo articleRepo;

    public List<Article> getArticles() {
        return articleRepo.findAll();
    }
}
