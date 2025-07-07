package org.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.example.backend.model.User;
import org.example.backend.model.WatchlistItem;
import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.repository.WatchlistRepo;
import org.springframework.data.mongodb.core.query.Update;


@Service
@RequiredArgsConstructor
public class WatchlistService {
    private final WatchlistRepo watchlistRepo;
    private final IDService idService;

    // public WatchlistItem addWatchlistItem(WatchlistItemDTO itemDTO) {

    //     System.out.println("WatchlistItemDTO: " + itemDTO);

    //     WatchlistItem item = new WatchlistItem(idService.createID(), itemDTO.userEmail(), itemDTO.product());
    //     return watchlistRepo.save(item);
    // }

    public List<WatchlistItem> getWatchlistItems(String email) {
        return watchlistRepo.findAllByUserEmail(email);
    }

    public void deleteWatchlistItem(String id) {
        watchlistRepo.deleteById(id);
    }
}
