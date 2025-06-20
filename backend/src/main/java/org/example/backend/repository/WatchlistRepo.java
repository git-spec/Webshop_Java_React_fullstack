package org.example.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.example.backend.model.WatchlistItem;


@Repository
public interface WatchlistRepo extends MongoRepository<WatchlistItem, String>  {
    List<WatchlistItem> findAllByUserEmail(String email);
}
