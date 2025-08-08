package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.Connection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectionRepo extends MongoRepository<Connection, String> {
    List<Connection> findBySenderIdOrReceiverIdAndStatus(String senderId, String receiverId, String status);
    List<Connection> findByReceiverIdAndStatus(String receiverId, String status);
    Optional<Connection> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
