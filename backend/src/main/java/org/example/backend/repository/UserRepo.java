package org.example.backend.repository;

import org.example.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends MongoRepository<User, String> {
    User findByEmail(String email);
}
