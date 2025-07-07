package org.example.backend.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

import lombok.RequiredArgsConstructor;

import org.example.backend.exception.IllegalArgumentException;
import org.example.backend.exception.DuplicateException;
import org.example.backend.exception.AccessException;
import org.example.backend.model.User;


@Service
@RequiredArgsConstructor
public class UserService {
    private final MongoTemplate mongoTemp;

    private static final String ILLEGAL_ARGUMENT = "Angaben fehlen.";
    private static final String DUPLICATE = "Bereits in der Watchlist enthalten.";
    private static final String INTERNAL_ERROR = "Es ist ein Fehelr aufgetreten. Versuchen Sie es später noch einmal.";

    public UpdateResult updateWatchlist(
        String userID, 
        String productID
    ) throws IllegalArgumentException, DuplicateException, AccessException {
        // Checks for null
        if (userID == null || productID == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
        // Checks for duplicate
        Query query = new Query(Criteria.where("id").is(userID).and("watchlist").is(productID));
        boolean exists = mongoTemp.exists(query, User.class);
        if (exists) {
            throw new DuplicateException(DUPLICATE);
        }
        // Checks result
        Update update = new Update().addToSet("watchlist", productID);
        UpdateResult result = mongoTemp.updateFirst(
            new Query(Criteria.where("id").is(userID)),
            update,
            User.class
        );
        if (result.wasAcknowledged()) {
            return result;
        } else {
            throw new AccessException(INTERNAL_ERROR);
        }
    }
}
