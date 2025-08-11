package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Connection;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.ConnectionRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.enums.ConnectionStatus;
import com.EChowk.EChowk.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepo connectionRepo;
    private final UserRepo userRepo;
    public Connection sendRequest(String senderId, String receiverId) {
        if(senderId.equals(receiverId)){
            throw new IllegalArgumentException("You cannot connect with yourself");
        }
        userRepo.findById(senderId).orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
        userRepo.findById(receiverId).orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));
        connectionRepo.findBySenderIdAndReceiverId(senderId,receiverId).ifPresent( c -> {
            throw new RuntimeException("Connection request already exists");
        });
        Connection connection = Connection.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .status(ConnectionStatus.PENDING)
                .build();
        connection.onCreate();
        return connectionRepo.save(connection);
    }
    public List<User> getUserConnections(String userId) {
        List<Connection> connections = connectionRepo
                .findBySenderIdOrReceiverIdAndStatus(userId,userId,ConnectionStatus.ACCEPTED);
        List<String> friends = connections.stream() .map(c -> c.getSenderId().equals(userId) ? c.getReceiverId() : c.getSenderId()).toList();
        return userRepo.findAllById(friends);
    }

    public List<Connection> getPendingRequests(String userId) {
        return connectionRepo.findByReceiverIdAndStatus(userId, ConnectionStatus.PENDING);
    }
    public Connection respondToRequestSecure(String requestId, String action, String userId) {
        Connection connection = connectionRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (connection.getStatus() != ConnectionStatus.PENDING) {
            throw new RuntimeException("This request is no longer pending");
        }

        boolean isSender = connection.getSenderId().equals(userId);
        boolean isReceiver = connection.getReceiverId().equals(userId);

        if (!isSender && !isReceiver) {
            throw new AccessDeniedException("You are not authorized to modify this request");
        }

        // Receiver actions
        if (isReceiver) {
            if ("ACCEPT".equalsIgnoreCase(action)) {
                connection.setStatus(ConnectionStatus.ACCEPTED);
            } else if ("DECLINE".equalsIgnoreCase(action)) {
                connection.setStatus(ConnectionStatus.DECLINED);
            } else {
                throw new IllegalArgumentException("Invalid action. Use ACCEPT or DECLINE.");
            }
        }

        // Sender actions
        else if (isSender) {
            if ("CANCEL".equalsIgnoreCase(action)) {
                connection.setStatus(ConnectionStatus.CANCELLED); // Add this to your ConnectionStatus enum
            } else {
                throw new IllegalArgumentException("Invalid action. Use CANCEL.");
            }
        }

        connection.onUpdate();
        return connectionRepo.save(connection);
    }
    public List<Connection> getPendingRequestsSecure(String currentUserId) {
        // Pending requests *for* the current user (receiver perspective)
        return connectionRepo.findByReceiverIdAndStatus(currentUserId, ConnectionStatus.PENDING);
    }

}
