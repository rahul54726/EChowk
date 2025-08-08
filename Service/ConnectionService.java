package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Connection;
import com.EChowk.EChowk.Repository.ConnectionRepo;
import com.EChowk.EChowk.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionService {
    private final ConnectionRepo connectionRepo;

    public Connection sendRequest(String sendSerId,String receiverId){
        if (connectionRepo.findBySenderIdAndReceiverId(sendSerId,receiverId).isPresent()){
            throw new RuntimeException("Connection request Already exists");
        }
        Connection connection = Connection.builder().
                senderId(sendSerId)
                .receiverId(receiverId)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        return connectionRepo.save(connection);
    }
    public Connection respondToRequest(String requestId, String action){
        Connection connection = connectionRepo.findById(requestId).orElseThrow(() -> new ResourceNotFoundException("Request Not found"));
        if(!action.equalsIgnoreCase("ACCEPTED") && !action.equalsIgnoreCase("DECLINED")){
            throw new RuntimeException("Invalid Action");
        }
        connection.setStatus(action.toUpperCase());
        return connectionRepo.save(connection);
    }
    public List<Connection> getUserConnections(String userId){
        return connectionRepo.findBySenderIdOrReceiverIdAndStatus(userId,userId,"ACCEPTED");
    }
    public List<Connection> getPendingRequests(String userId){
        return connectionRepo.findByReceiverIdAndStatus(userId,"PENDING");
    }
}
