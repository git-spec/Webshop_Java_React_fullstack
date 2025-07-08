package org.example.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.UpdateResult;

import lombok.AllArgsConstructor;

import org.example.backend.model.WatchlistItem;
import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.service.UserService;
import org.example.backend.service.WatchlistService;


@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final WatchlistService watchlistService;
    private final UserService userService;

    @GetMapping("/watchlist/{email}")
    public List<WatchlistItem> getWatchlistItems(@PathVariable String email) {
        return watchlistService.getWatchlistItems(email);
    }

    // @PostMapping("/watchlist")
    // public WatchlistItem addWatchlistItem(@RequestBody WatchlistItemDTO itemDTO) {

    //     System.out.println("WatchlistItemDTO: " + itemDTO);

    //     return watchlistService.addWatchlistItem(itemDTO);
    // }

    @DeleteMapping("/watchlist/{id}")
    public void deleteWatchlistItem(@PathVariable String id) {
        watchlistService.deleteWatchlistItem(id);
    }

    @PutMapping("/watchlist")
    public UpdateResult updateWatchlist(@RequestBody WatchlistItemDTO body) {
        return userService.updateWatchlist(body.idToken(), body.productID());
    }
} 
