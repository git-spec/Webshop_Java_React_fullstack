package org.example.backend.service;

import java.util.List;

import org.example.backend.exception.AccessException;
import org.example.backend.exception.DuplicateException;
import org.example.backend.exception.IllegalArgumentException;
import org.example.backend.model.User;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private MongoTemplate mockTemp;
    @Mock
    private UpdateResult mockResult;
    @InjectMocks
    private UserService userService;

    @Test
    void updateWathlist_shouldUpdateWatchlist_whenGetUserIdAndProductId() {
        // GIVEN
        UpdateResult updateResult = mock(UpdateResult.class);
        User user = new User("test123", "123@test.com", "Jon", "Doe", List.of());
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
    void updateWatchlist_shouldThrowCustomException_onDataAccessException() throws AccessException {
        // WHEN
        when(mockResult.wasAcknowledged()).thenReturn(true);
        when(mockTemp.updateFirst(any(Query.class), any(Update.class), eq(User.class)))
            .thenReturn(mockResult);
        UpdateResult result = userService.updateWatchlist("userId", "productId");
        // THEN
        assertSame(mockResult, result);
    }
}