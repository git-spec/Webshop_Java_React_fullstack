package org.example.backend.repository;

import org.example.backend.model.dto.OrderCompletedDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderRepo extends MongoRepository<OrderCompletedDTO, String> {
}
