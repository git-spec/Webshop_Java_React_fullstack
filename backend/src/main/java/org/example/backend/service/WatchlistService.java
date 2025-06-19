package org.example.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.example.backend.model.WatchlistItem;
import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.repository.WatchlistRepo;


@Service
@RequiredArgsConstructor
public class WatchlistService {
    private final WatchlistRepo watchlistRepo;
    private final IDService idService;

    private static final String SAVED_ITEM_SUCCESSFULLY = "Item was saved successfully.";

    // public List<WatchlistItem> getWatchlist(String email) {
    //     return watchlistRepo.findAllByEmail(email);
    // }

    public ResponseEntity<String> addWatchlistItem(WatchlistItemDTO itemDTO) {
        WatchlistItem item = new WatchlistItem(idService.createID(), itemDTO.userEmail(), itemDTO.product());
        watchlistRepo.save(item);
        return ResponseEntity.status(HttpStatus.OK).body(SAVED_ITEM_SUCCESSFULLY);
    }
}
