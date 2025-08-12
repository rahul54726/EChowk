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

    // ✅ Respond to request (authenticated user only)
    @PostMapping("/{requestId}/respond")
    public ResponseEntity<Connection> respondToRequestSecure(
            @PathVariable String requestId,
            @RequestParam String action
    ) {
        String userId = getAuthenticatedUserId();
        return ResponseEntity.ok(connectionService.respondToRequestSecure(requestId, action, userId));
    }

    // ✅ Get friends of a specific user
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<User>> getUserConnections(@PathVariable String userId) {
        List<User> connections = connectionService.getUserConnections(userId);
        return ResponseEntity.ok(connections);
    }

    // ✅ Get pending requests (only for logged-in user)
    @GetMapping("/pending")
    public ResponseEntity<List<Connection>> getPendingRequests() {
        String currentUserId = getAuthenticatedUserId();
        return ResponseEntity.ok(connectionService.getPendingRequestsSecure(currentUserId));
    }

    // ✅ Send request (authenticated user as sender)
    @PostMapping("/send")
    public ResponseEntity<Connection> sendRequest(@RequestParam String receiverId) {
        String senderId = getAuthenticatedUserId();
        Connection connection = connectionService.sendRequest(senderId, receiverId);
        return ResponseEntity.ok(connection);
    }

    // ✅ Extract userId from authenticated principal
    private String getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getId(); // Directly get DB ID
        }
        throw new RuntimeException("User not authenticated or invalid principal");
    }
}
