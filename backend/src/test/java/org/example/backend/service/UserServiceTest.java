package org.example.backend.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
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

import org.example.backend.Utils;
import org.example.backend.exception.AccessException;
import org.example.backend.exception.DuplicateException;
import org.example.backend.exception.InvalidArgumentException;
import org.example.backend.model.Address;
import org.example.backend.model.User;
import org.example.backend.model.dto.UserDTO;
import org.example.backend.model.dto.WatchlistItemDTO;
import org.example.backend.repository.UserRepo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;


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
    
    private final String id = "test123";
    private final Address address = new Address("street", "postal", "locality", "region", "country");
    private final User user = new User(id, "123@test.com", "Jon", "Doe", Instant.now());
    private final UserDTO userDTO = new UserDTO("123@test.com", "Jon", "Doe");
    private final WatchlistItemDTO itemDTO = new WatchlistItemDTO(id, "prod1");
    private final WatchlistItemDTO invalidItemDto = new WatchlistItemDTO(null, "prod1");

    @Test
    void getUser_shoulReturnUser_whenGetEmail() {
        // GIVEN
        user.setAddress(address);
        user.setWatchlist(List.of());
        mockRepo.save(user);
        String email = user.getEmail();
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            // WHEN
            mockedStatic.when(() -> Utils.isValidEmail(anyString())).thenReturn(true);
            when(mockRepo.findByEmail(email)).thenReturn(Optional.of(user));
            Utils.isValidEmail(user.getEmail());
            User actual = userService.getUser(email);
            // THEN
            assertEquals(user, actual);
            assertEquals(user.getAddress(), actual.getAddress());
            verify(mockRepo, times(1)).findByEmail(email);
        }
    }

    @Test
    void getUser_shouldThrowAccessException_onDataAccessException() throws AccessException {
        // GIVEN
        String email = user.getEmail();
        // WHEN // THEN
        AccessException exception = assertThrows(
            AccessException.class, 
            () -> userService.getUser(email)
        );
        assertEquals(UserService.INTERNAL_ERROR, exception.getMessage());
    }

    @Test
    void getUser_shouldIllegalArgumentException_whenGetInvalidID() throws InvalidArgumentException {
        // GIVEN
        // WHEN
        Boolean result = Utils.isValidEmail("jondoe.io");
        // THEN
        assertFalse(result, UserService.ILLEGAL_ARGUMENT);
    }

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
    void addUser_shouldThrowIllegalArgumentException_whenGetNull() throws InvalidArgumentException {
        // WHEN
        UserDTO invalidDTO = new UserDTO(null, "Jon", "Doe");
        // THEN
        InvalidArgumentException exception = assertThrows(
            InvalidArgumentException.class, 
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
    void addUser_shouldThrowAccessException_onDataAccessException() throws AccessException {
        // THEN
        AccessException exception = assertThrows(
            AccessException.class, 
            () -> userService.addUser(userDTO)
        );
        assertEquals(UserService.INTERNAL_ERROR, exception.getMessage());
    }

    @Test
    void updateWatchlist_shouldUpdateWatchlist_whenGetUserIdAndProductId() {
        // GIVEN
        UpdateResult updateResult = mock(UpdateResult.class);
        user.setWatchlist(List.of());
        mockTemp.save(user);
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            // WHEN
            mockedStatic.when(() -> Utils.isValidAlphanumeric(anyString())).thenReturn(true);
            when(updateResult.wasAcknowledged()).thenReturn(true);
            when(mockTemp.updateFirst(any(Query.class), any(Update.class), eq(User.class)))
                .thenReturn(updateResult);
            UpdateResult actual = userService.updateWatchlist(itemDTO);
            Utils.isValidAlphanumeric(id);
            // THEN
            assertEquals(updateResult, actual);
            mockedStatic.verify(() -> Utils.isValidAlphanumeric(id), times(2));
            verify(mockTemp).updateFirst(any(Query.class), any(Update.class), eq(User.class));
        }
    }

    @Test
    void updateWatchlist_shouldThrowIllegalArgumentException_whenGetInvalidData() throws InvalidArgumentException {
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            // WHEN
            mockedStatic.when(() -> Utils.isValidAlphanumeric(anyString())).thenReturn(false);
            when(Utils.isValidAlphanumeric(any())).thenReturn(false);
            Utils.isValidAlphanumeric(null);
            // THEN
            mockedStatic.verify(() -> Utils.isValidAlphanumeric(null));
            InvalidArgumentException actual = assertThrows(
                InvalidArgumentException.class, 
                () -> userService.updateWatchlist(invalidItemDto)
            );
            assertEquals(UserService.ILLEGAL_ARGUMENT, actual.getMessage());
        }
    }

    @Test
    void updateWatchlist_shouldThrowDuplicateException_whenProductExist() throws DuplicateException {
        // GIVEN
        user.setWatchlist(List.of("prod1"));
        mockTemp.save(user);
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            // WHEN
            mockedStatic.when(() -> Utils.isValidAlphanumeric(anyString())).thenReturn(true);
            when(Utils.isValidAlphanumeric(any())).thenReturn(true);
            Utils.isValidAlphanumeric(id);
            when(
                mockTemp.exists(
                    new Query(Criteria.where("id")
                        .is(itemDTO.userID())
                        .and("watchlist").is(itemDTO.productID())), 
                    User.class
                )
            
            ).thenThrow(new DuplicateException(UserService.DUPLICATE));
            // THEN
            mockedStatic.verify(() -> Utils.isValidAlphanumeric(id));
            DuplicateException actual = assertThrows(
                DuplicateException.class, 
                () -> userService.updateWatchlist(itemDTO)
            );
            assertEquals(UserService.DUPLICATE, actual.getMessage());
        }
    }

    @Test
    void updateWatchlist_shouldThrowAccessException_onDataAccessException() throws AccessException {
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            // WHEN
            mockedStatic.when(() -> Utils.isValidAlphanumeric(anyString())).thenReturn(true);
            when(Utils.isValidAlphanumeric(any())).thenReturn(true);
            Utils.isValidAlphanumeric(id);
            when(mockResult.wasAcknowledged()).thenReturn(true);
            when(mockTemp.updateFirst(any(Query.class), any(Update.class), eq(User.class)))
                .thenReturn(mockResult);
            UpdateResult result = userService.updateWatchlist(itemDTO);
            // THEN
            mockedStatic.verify(() -> Utils.isValidAlphanumeric(id), times(2));
            assertSame(mockResult, result);
        }
    }

    @Test
    void removeWatchlistItem_shouldRemoveWatchlistItem_whenGetUserIdAndProductId() {
        // GIVEN
        UpdateResult updateResult = mock(UpdateResult.class);
        user.setWatchlist(List.of("prod1", "prod2", "prod3"));
        mockTemp.save(user);
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            // WHEN
            mockedStatic.when(() -> Utils.isValidAlphanumeric(anyString())).thenReturn(true);
            when(updateResult.wasAcknowledged()).thenReturn(true);
            when(mockTemp.updateFirst(any(Query.class), any(Update.class), eq(User.class)))
                .thenReturn(updateResult);
            Utils.isValidAlphanumeric(id);
            UpdateResult actual = userService.removeWatchlistItem(itemDTO);
            // THEN
            assertEquals(updateResult, actual);
            mockedStatic.verify(() -> Utils.isValidAlphanumeric(id), times(2));
            verify(mockTemp).updateFirst(any(Query.class), any(Update.class), eq(User.class));
        }
    }

    @Test
    void removeWatchlistItem_shouldThrowIllegalArgumentException_whenGetInvalidData() throws InvalidArgumentException {
        // THEN
        InvalidArgumentException actual = assertThrows(
            InvalidArgumentException.class, 
            () -> userService.removeWatchlistItem(invalidItemDto)
        );
        assertEquals(UserService.ILLEGAL_ARGUMENT, actual.getMessage());
    }

    @Test
    void removeWatchlistItem_shouldThrowAccessException_onDataAccessException() throws AccessException {
        // GIVEN
        user.setWatchlist(List.of("prod1"));
        mockTemp.save(user);
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            // WHEN
            mockedStatic.when(() -> Utils.isValidAlphanumeric(anyString())).thenReturn(true);
            when(Utils.isValidAlphanumeric(any())).thenReturn(true);
            Utils.isValidAlphanumeric(id);
            when(mockResult.wasAcknowledged()).thenReturn(true);
            when(mockTemp.updateFirst(any(Query.class), any(Update.class), eq(User.class)))
                .thenReturn(mockResult);
            UpdateResult result = userService.removeWatchlistItem(itemDTO);
            // THEN
            mockedStatic.verify(() -> Utils.isValidAlphanumeric(id), times(2) );
            assertSame(mockResult, result);
        }
    }
}