package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.Connection;
import com.EChowk.EChowk.enums.ConnectionStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ConnectionRepo extends MongoRepository<Connection, String> {
    List<Connection> findByReceiverIdAndStatus(String receiverId, ConnectionStatus status);
    List<Connection> findBySenderIdAndStatus(String senderId, ConnectionStatus status);
    Optional<Connection> findBySenderIdAndReceiverId(String senderId,String receiverId);
    List<Connection> findBySenderIdOrReceiverIdAndStatus(String senderId, String receiverId ,ConnectionStatus status);

    long countBySenderIdOrReceiverIdAndStatus(String userId, String userId1, ConnectionStatus connectionStatus);
}
