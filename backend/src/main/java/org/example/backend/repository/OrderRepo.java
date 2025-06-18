package org.example.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.example.backend.model.OrderCompleted;


@Repository
public interface OrderRepo extends MongoRepository<OrderCompleted, String> {
    List<OrderCompleted> findByPaypalEmail(String email);

}
