package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.Connection;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/send")
    public ResponseEntity<Connection> sendRequest(
            @RequestParam String senderId,
            @RequestParam String receiverId
    ){
        return ResponseEntity.ok(connectionService.sendRequest(senderId,receiverId));

    }
    @PostMapping("/{requestId}/respond")
    public ResponseEntity<Connection> respondToRequestSecure(
            @PathVariable String requestId,
            @RequestParam String action,
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(connectionService.respondToRequestSecure(requestId, action, userId));
    }
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<User>> getUserConnections(@PathVariable String userId) {
        List<User> connections = connectionService.getUserConnections(userId);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Connection>> getPendingRequests(Authentication authentication) {
        // Assuming your JWT Authentication stores userId as the username
        String currentUserId = authentication.getName();
        return ResponseEntity.ok(connectionService.getPendingRequestsSecure(currentUserId));
    }


    @PostMapping("/request")
    public ResponseEntity<Connection> sendRequest(@RequestParam String receiverId) {
        String senderId = getAuthenticatedUserId();
        Connection connection = connectionService.sendRequest(senderId, receiverId);
        return ResponseEntity.ok(connection);
    }

    @PostMapping("/respond/{requestId}")
    public ResponseEntity<Connection> respondToRequest(@PathVariable String requestId,
                                                       @RequestParam String action) {
        String userId = getAuthenticatedUserId();
        Connection connection = connectionService.respondToRequestSecure(requestId, action, userId);
        return ResponseEntity.ok(connection);
    }

    private String getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Or custom logic from your UserDetails
    }

}
