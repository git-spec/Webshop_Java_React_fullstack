package org.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.service.WatchlistService;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class WatchlistController {
    private final WatchlistService watchlistService;

    @PostMapping("/watchlist")
    public ResponseEntity<String> addWatchlistItem(WatchlistItemDTO item) throws Exception {
        return watchlistService.addWatchlistItem(item);
    }
}
