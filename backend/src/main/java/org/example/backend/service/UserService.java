package org.example.backend.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

import lombok.RequiredArgsConstructor;

import org.example.backend.exception.InvalidArgumentException;
import org.example.backend.exception.DuplicateException;
import org.example.backend.Utils;
import org.example.backend.exception.AccessException;
import org.example.backend.model.User;
import org.example.backend.model.dto.UserDTO;
import org.example.backend.repository.UserRepo;
import org.example.backend.model.dto.WatchlistItemDTO;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final MongoTemplate mongoTemp;
    private final IDService idService;

    static final String ILLEGAL_ARGUMENT = "Angaben fehlen.";
    static final String DUPLICATE = "Bereits in der Watchlist enthalten.";
    static final String INTERNAL_ERROR = "Es ist ein Fehelr aufgetreten. Versuchen Sie es später noch einmal.";

    public User getUser(String email) throws InvalidArgumentException, AccessException {
        if (Utils.isValidEmail(email)) {
            Optional<User> result = userRepo.findByEmail(email);
            if (!result.isEmpty()) {
                return result.get(); 
            } else {
                throw new AccessException(INTERNAL_ERROR);
            }
        } else {
            throw new InvalidArgumentException(ILLEGAL_ARGUMENT);
        }
    }

    public User addUser(UserDTO userDTO) throws DuplicateException {
        User user = new User(
            idService.createID(), 
            userDTO.getEmail(),
            userDTO.getGivenName(),
            userDTO.getFamilyName(),
            Instant.now()
        );
        user.setSub(userDTO.getSub());
        user.setGender(userDTO.getGender());
        user.setAddress(userDTO.getAddress());
        user.setWatchlist(userDTO.getWatchlist());
        // Checks for nullWie kann ich das testen: 
        if (user.getEmail() == null) {
            throw new InvalidArgumentException(ILLEGAL_ARGUMENT);
        }

        // Checks for duplicate
        Optional<User> existing = userRepo.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            throw new DuplicateException(DUPLICATE);
        }
        // Checks result
        User result = userRepo.save(user);
        if (result instanceof User) {
            return userRepo.save(user);
        } else {
            throw new AccessException(INTERNAL_ERROR);
        }

    }

    public UpdateResult updateWatchlist(
        WatchlistItemDTO itemDto
    ) throws InvalidArgumentException, DuplicateException, AccessException {
        // Checks for null
        if (!Utils.isValidAlphanumeric(itemDto.userID()) || !Utils.isValidAlphanumeric(itemDto.productID())) {
            throw new InvalidArgumentException(ILLEGAL_ARGUMENT);
        }
        // Checks for duplicate
        Query query = new Query(
                Criteria.where("id")
                .is(itemDto.userID())
                .and("watchlist")
                .is(itemDto.productID()
            )
        );
        boolean exists = mongoTemp.exists(query, User.class);
        if (exists) {
            throw new DuplicateException(DUPLICATE);
        }
        // Checks result
        Update update = new Update().addToSet("watchlist", itemDto.productID());
        UpdateResult result = mongoTemp.updateFirst(
            new Query(Criteria.where("id").is(itemDto.userID())),
            update,
            User.class
        );
        if (result.wasAcknowledged()) {
            return result;
        } else {
            throw new AccessException(INTERNAL_ERROR);
        }
    }

    public UpdateResult removeWatchlistItem(WatchlistItemDTO itemDto) throws InvalidArgumentException, AccessException {
        // Checks for null
        if (!Utils.isValidAlphanumeric(itemDto.userID()) || !Utils.isValidAlphanumeric(itemDto.productID())) {
            throw new InvalidArgumentException(ILLEGAL_ARGUMENT);
        }
        // Checks result
        Query query = new Query(Criteria.where("_id").is(itemDto.userID()));
        Update update = new Update().pull("watchlist", itemDto.productID());
        UpdateResult result  = mongoTemp.updateFirst(query, update, User.class);
        if (result.wasAcknowledged()) {
            return result;
        } else {
            throw new AccessException(INTERNAL_ERROR);
        }
    }
}
