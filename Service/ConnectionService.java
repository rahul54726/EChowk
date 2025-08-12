package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Connection;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.ConnectionRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.enums.ConnectionStatus;
import com.EChowk.EChowk.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepo connectionRepo;
    private final UserRepo userRepo;
    private final ConnectionEmailService connectionEmailService; // ✅ Inject Email Service

    public Connection sendRequest(String senderId, String receiverId) {
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("You cannot connect with yourself");
        }
        User sender = userRepo.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
        User receiver = userRepo.findById(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

        connectionRepo.findBySenderIdAndReceiverId(senderId, receiverId)
                .ifPresent(c -> {
                    throw new RuntimeException("Connection request already exists");
                });

        Connection connection = Connection.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .status(ConnectionStatus.PENDING)
                .build();
        connection.onCreate();
        Connection saved = connectionRepo.save(connection);

        // Send email notification to receiver
        connectionEmailService.sendConnectionEmail(
                receiver.getEmail(),
                "New Connection Request",
                sender.getName() + " has sent you a connection request."
        );

        return saved;
    }


    public List<User> getUserConnections(String userId) {
        List<Connection> connections = connectionRepo
                .findBySenderIdOrReceiverIdAndStatus(userId, userId, ConnectionStatus.ACCEPTED);
        List<String> friends = connections.stream()
                .map(c -> c.getSenderId().equals(userId) ? c.getReceiverId() : c.getSenderId())
                .toList();
        return userRepo.findAllById(friends);
    }

    public List<Connection> getPendingRequests(String userId) {
        return connectionRepo.findByReceiverIdAndStatus(userId, ConnectionStatus.PENDING);
    }

    public Connection respondToRequestSecure(String requestId, String action, String userId) {
        Connection connection = connectionRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (connection.getStatus() != ConnectionStatus.PENDING &&
                !"CANCEL".equalsIgnoreCase(action)) {
            throw new RuntimeException("This request is no longer pending");
        }

        boolean isSender = connection.getSenderId().equals(userId);
        boolean isReceiver = connection.getReceiverId().equals(userId);

        if (!isSender && !isReceiver) {
            throw new AccessDeniedException("You are not authorized to modify this request");
        }

        User sender = userRepo.findById(connection.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
        User receiver = userRepo.findById(connection.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

        if (isReceiver) {
            if ("ACCEPT".equalsIgnoreCase(action)) {
                connection.setStatus(ConnectionStatus.ACCEPTED);
                connectionEmailService.sendConnectionEmail(
                        sender.getEmail(),
                        "Connection Accepted",
                        receiver.getName() + " has accepted your connection request."
                );
            } else if ("DECLINE".equalsIgnoreCase(action)) {
                connection.setStatus(ConnectionStatus.DECLINED);
                connectionEmailService.sendConnectionEmail(
                        sender.getEmail(),
                        "Connection Declined",
                        receiver.getName() + " has declined your connection request."
                );
            } else {
                throw new IllegalArgumentException("Invalid action. Use ACCEPT or DECLINE.");
            }
        } else if (isSender && "CANCEL".equalsIgnoreCase(action)) {
            connection.setStatus(ConnectionStatus.CANCELLED);
            connectionEmailService.sendConnectionEmail(
                    receiver.getEmail(),
                    "Connection Cancelled",
                    sender.getName() + " has cancelled the connection request."
            );
        } else {
            throw new IllegalArgumentException("Invalid action. Use CANCEL.");
        }

        connection.onUpdate();
        return connectionRepo.save(connection);
    }

    public List<Connection> getPendingRequestsSecure(String currentUserId) {
        return connectionRepo.findByReceiverIdAndStatus(currentUserId, ConnectionStatus.PENDING);
    }
}
