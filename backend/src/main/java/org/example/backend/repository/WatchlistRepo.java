package org.example.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.example.backend.model.WatchlistItem;

import org.example.backend.model.WatchlistItem;


@Document("watchlists")
public interface WatchlistRepo extends MongoRepository<WatchlistItem, String>  {}
