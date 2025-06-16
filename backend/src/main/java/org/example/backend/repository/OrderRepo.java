package org.example.backend.repository;

import org.example.backend.model.OrderCompleted;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderRepo extends MongoRepository<OrderCompleted, String> {
}
