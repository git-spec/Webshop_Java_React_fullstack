package org.example.backend.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.example.backend.exception.AccessException;
import org.example.backend.exception.DuplicateException;
import org.example.backend.exception.IllegalArgumentException;
import org.example.backend.model.User;
import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.model.dto.UserDTO;
import org.example.backend.repository.UserRepo;
import org.example.backend.service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private  UserRepo userRepo;
    @MockitoBean
    private  UserService mockService;
    @Autowired
    private ObjectMapper objectMapper;

    UpdateResult updateResult = mock(UpdateResult.class);
    private final User user = new User("test123", "123@test.com", "Jon", "Doe", Instant.now());
    private final UserDTO userDTO = new UserDTO("123@test.com", "Jon", "Doe");
    private final WatchlistItemDTO itemDto = new WatchlistItemDTO("test123", "prod1");
    private final WatchlistItemDTO invalidItemDto = new WatchlistItemDTO(null, "prod1");

    @Test
    void getUser_shouldReturnUser_whenGetEmail() throws Exception {
        // GIVEN
        userRepo.save(user);
        // WHEN
        when(mockService.getUser(user.getEmail())).thenReturn(user);
        // THEN
        mockMvc.perform(get("/api/user/{email}", user.getEmail()))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(user)));
    }

    @Test
    void getUser_shouldThrow400_whenGetInvaldEmail() throws Exception {
        // GIVEN
        userRepo.save(user);
        // WHEN
        when(mockService.getUser("INVALID"))
            .thenThrow(new IllegalArgumentException("Invalid"));
        // THEN
        mockMvc.perform(get("/api/user/{email}", "INVALID"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getUser_shouldThrow500_whenGetInvaldEmail() throws Exception {
        // WHEN 
        when(mockService.getUser("jondoe.io"))
            .thenThrow(new AccessException("Fehler"));
        // THEN
        mockMvc.perform(get("/api/user/{id}", "jondoe.io"))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void addUser_shouldReturnUser_whenGetUser() throws Exception {
        // GIVEN
        userRepo.save(user);
        String jsonDTO = objectMapper.writeValueAsString(userDTO);
        // WHEN
        when(mockService.addUser(userDTO)).thenReturn(user);
        // THEN
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO))
            .andExpect(status().isOk());
    }

    @Test
    void addUser_shouldThrow409_whenUserExist() throws Exception {
        // GIVEN
        String jsonDTO = objectMapper.writeValueAsString(userDTO);
        // WHEN
        when(mockService.addUser(userDTO))
            .thenThrow(new DuplicateException("Fehler"));
        // THEN
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO))
            .andExpect(status().isConflict());
    }

    @Test
    void addUser_shouldThrow400_whenGetNull() throws Exception {
        // WHEN
        when(mockService.addUser(any(UserDTO.class)))
            .thenThrow(new IllegalArgumentException("Fehler"));
        // THEN
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """      
                """
            ))
            .andExpect(status().isBadRequest());
    }

    @Test
    void addUser_shouldThrow500_whenUpdateFailed() throws Exception {
        // GIVEN
        String jsonDTO = objectMapper.writeValueAsString(userDTO);
        // WHEN 
        when(mockService.addUser(userDTO))
            .thenThrow(new AccessException("Fehler"));
        // THEN
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO)).andExpect(status().isInternalServerError());
    }

    @Test
    void updateWatchlist_shouldAddProductToWatchlist_whenGetIdTokenAndProductId() throws Exception {
        // GIVEN
        String jsonDTO = objectMapper.writeValueAsString(itemDto);
        UpdateResult result = UpdateResult.acknowledged(1L, 1L, null);
        user.setWatchlist(List.of());
        userRepo.save(user);
        // WHEN 
        when(mockService.updateWatchlist(itemDto)).thenReturn(result); 
        // THEN
        mockMvc.perform(put("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO))
            .andExpect(status().isOk());
        verify(userRepo).save(argThat(el ->
            el.getId().equals("test123") &&
            el.getEmail().equals("123@test.com") &&
            el.getGivenName().equals("Jon") &&
            el.getFamilyName().equals("Doe") &&
            el.getWatchlist().isEmpty()
        ));
    }

    @Test
    void updateWatchlist_shouldThrow500_whenUpdateFailed() throws Exception {
        // WHEN 
        String jsonDTO = objectMapper.writeValueAsString(itemDto);
        when(updateResult.wasAcknowledged()).thenReturn(false);
        when(mockService.updateWatchlist(new WatchlistItemDTO(anyString(), anyString())))
            .thenThrow(new AccessException("Fehler"));
        // THEN
        mockMvc.perform(put("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void updateWatchlist_shouldThrow409_whenProductExist() throws Exception {
        // GIVEN
        String jsonDTO = objectMapper.writeValueAsString(itemDto);
        user.setWatchlist(List.of("prod1"));
        userRepo.save(user);
        // WHEN
        when(mockService.updateWatchlist(itemDto))
            .thenThrow(new DuplicateException("Fehler"));
        // THEN
        mockMvc.perform(put("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO))
            .andExpect(status().isConflict());
    }

    @Test
    void updateWatchlist_shouldThrow400_whenGetNull() throws Exception {
        // GIVEN
        String jsonDTO = objectMapper.writeValueAsString(invalidItemDto);
        // WHEN 
        when(mockService.updateWatchlist(invalidItemDto))
            .thenThrow(new IllegalArgumentException("Fehler"));
        // THEN
        mockMvc.perform(put("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDTO))
            .andExpect(status().isBadRequest());
    }

    @Test
    void removeWatchlistItem_shouldRemovesItemFromWatchlist_whenGetUserIDandProductID() throws Exception {
        // GIVEN
        UpdateResult result = UpdateResult.acknowledged(1L, 1L, null);
        WatchlistItemDTO dto = new WatchlistItemDTO("test123", "prod2");
        String jsonDto = objectMapper.writeValueAsString(dto);
        List<String> updated = List.of("prod1", "prod3");
        user.setWatchlist(updated);
        userRepo.save(user);
        // WHEN 
        when(mockService.removeWatchlistItem(dto)).thenReturn(result); 
        // THEN
        mockMvc.perform(post("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDto))
            .andExpect(status().isOk());
        verify(userRepo).save(argThat(el ->
            el.getId().equals("test123") &&
            el.getEmail().equals("123@test.com") &&
            el.getGivenName().equals("Jon") &&
            el.getFamilyName().equals("Doe") &&
            el.getWatchlist().equals(updated)
        ));
    }

    @Test
    void removeWatchlistItem_shouldThrow400_whenGetNull() throws Exception {
        // GIVEN
        String jsonDto = objectMapper.writeValueAsString(invalidItemDto);
        // WHEN 
        when(mockService.removeWatchlistItem(invalidItemDto))
            .thenThrow(new IllegalArgumentException("Fehler"));
        // THEN
        mockMvc.perform(post("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDto))
            .andExpect(status().isBadRequest());
    }

    @Test
    void removeWatchlistItem_shouldThrow500_whenUpdateFailed() throws Exception {
        // GIVEN
        String jsonDto = objectMapper.writeValueAsString(itemDto);
        // WHEN 
        when(updateResult.wasAcknowledged()).thenReturn(false);
        when(mockService.updateWatchlist(new WatchlistItemDTO(anyString(), anyString())))
            .thenThrow(new AccessException("Fehler"));
        // THEN
        mockMvc.perform(post("/api/user/watchlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonDto))
            .andExpect(status().isInternalServerError());
    }
}
