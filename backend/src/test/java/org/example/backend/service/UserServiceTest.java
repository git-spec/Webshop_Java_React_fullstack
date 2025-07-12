package org.example.backend.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mongodb.client.result.UpdateResult;

import org.example.backend.exception.AccessException;
import org.example.backend.exception.DuplicateException;
import org.example.backend.exception.IllegalArgumentException;
import org.example.backend.model.User;
import org.example.backend.model.dto.UserDTO;
import org.example.backend.repository.UserRepo;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    IDService mockID;
    @Mock
    private UserRepo mockRepo;
    @Mock
    private MongoTemplate mockTemp;
    @Mock
    private UpdateResult mockResult;
    @InjectMocks
    private UserService userService;
    
    User user = new User("test123", "123@test.com", "Jon", "Doe", Instant.now());
    UserDTO userDTO = new UserDTO("123@test.com", "Jon", "Doe");

    @Test
    void addUser_shouldAddUserToDB_whenGetUserDTO() {
        // GIVEN
        mockRepo.save(user);
        // WHEN
        when(mockID.createID()).thenReturn("test123");
        when(mockRepo.save(any(User.class))).thenReturn(user);
        User actual = userService.addUser(userDTO);
        String id = mockID.createID();
        // THEN
        assertEquals(user, actual);
        assertEquals("test123", id);
        verify(mockRepo, times(1)).save(user);
    }

    @Test
    void addUsert_shouldThrowIllegalArgumentException_whenGetNull() throws IllegalArgumentException {
        // WHEN
        UserDTO invalidDTO = new UserDTO(null, "Jon", "Doe");
        // THEN
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> userService.addUser(invalidDTO)
        );
        assertEquals(UserService.ILLEGAL_ARGUMENT, exception.getMessage());
    }

    @Test
    void addUser_shouldThrowDuplicateException_whenUserExist() throws DuplicateException {
        // GIVEN
        mockRepo.save(user);
        // WHEN
        when(mockRepo.findByEmail(user.getEmail())).thenThrow(new DuplicateException(UserService.DUPLICATE));
        // THEN
        DuplicateException exception = assertThrows(DuplicateException.class, () -> 
            userService.addUser(userDTO)
        );
        assertEquals(UserService.DUPLICATE, exception.getMessage());
    }

    @Test
    void addUsert_shouldThrowAccessException_onDataAccessException() throws AccessException {
        // THEN
        AccessException exception = assertThrows(
            AccessException.class, 
            () -> userService.addUser(userDTO)
        );
        assertEquals(UserService.INTERNAL_ERROR, exception.getMessage());
    }

    @Test
    void updateWathlist_shouldUpdateWatchlist_whenGetUserIdAndProductId() {
        // GIVEN
        UpdateResult updateResult = mock(UpdateResult.class);
        user.setWatchlist(List.of());
        mockTemp.save(user);
        // WHEN
        when(updateResult.wasAcknowledged()).thenReturn(true);
        when(mockTemp.updateFirst(any(Query.class), any(Update.class), eq(User.class)))
            .thenReturn(updateResult);
        UpdateResult actual = userService.updateWatchlist("test123", "prod123");
        // THEN
        assertEquals(updateResult, actual);
        verify(mockTemp).updateFirst(any(Query.class), any(Update.class), eq(User.class));

    }

    @Test
    void updateWatchlist_shouldThrowIllegalArgumentException_whenGetNull() throws IllegalArgumentException {
        // THEN
        assertThrows(
            IllegalArgumentException.class, 
            () -> userService.updateWatchlist(null, null)
        );
    }

    @Test
    void updateWatchlist_shouldThrowDuplicateException_whenProductExist() throws DuplicateException {
        // WHEN
        when(mockTemp.exists(any(Query.class),eq(User.class))).thenReturn(true);
        // THEN
        assertThrows(DuplicateException.class, () -> 
            userService.updateWatchlist("test123", "prod234")
        );
    }

    @Test
    void updateWatchlist_shouldThrowAccessException_onDataAccessException() throws AccessException {
        // WHEN
        when(mockResult.wasAcknowledged()).thenReturn(true);
        when(mockTemp.updateFirst(any(Query.class), any(Update.class), eq(User.class)))
            .thenReturn(mockResult);
        UpdateResult result = userService.updateWatchlist("userId", "productId");
        // THEN
        assertSame(mockResult, result);
    }
}