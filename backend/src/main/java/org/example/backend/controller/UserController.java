package org.example.backend.controller;

import java.util.List;

import org.example.backend.model.User;
import org.example.backend.model.WatchlistItem;
import org.example.backend.model.dto.UserDTO;
import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.UpdateResult;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    public User getUser(@PathVariable String email) {
        return userService.getUser(email);
    }

    @PostMapping
    public User addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("/watchlist")
    public void deleteWatchlistItem(@RequestBody WatchlistItemDTO itemDTO) {
        userService.removeWatchlistItem(itemDTO);
    }

    @PutMapping("/watchlist")
    public UpdateResult updateWatchlist(@RequestBody WatchlistItemDTO itemDTO) {
        return userService.updateWatchlist(itemDTO);
    }
} 
