package org.example.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;

import org.example.backend.model.WatchlistItem;
import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.service.WatchlistService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class WatchlistController {
    private final WatchlistService watchlistService;

    @GetMapping("/watchlist/{email}")
    public List<WatchlistItem> getWatchlistItems(@PathVariable String email) {
        return watchlistService.getWatchlistItems(email);
    }

    @PostMapping("/watchlist")
    public WatchlistItem addWatchlistItem(@RequestBody WatchlistItemDTO itemDTO) {

        System.out.println("WatchlistItemDTO: " + itemDTO);

        return watchlistService.addWatchlistItem(itemDTO);
    }

    @DeleteMapping("/watchlist/{id}")
    public void deleteWatchlistItem(@PathVariable String id) {
        watchlistService.deleteWatchlistItem(id);
    }
}
