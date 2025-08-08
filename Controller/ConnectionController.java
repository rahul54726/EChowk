package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.Connection;
import com.EChowk.EChowk.Service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("connections")
@RequiredArgsConstructor
public class ConnectionController {
    private final ConnectionService connectionService;

    @PostMapping("/request")
    public ResponseEntity<?> sendRequest(@RequestParam String senderId,@RequestParam String receiverId){
        return new ResponseEntity<>(connectionService.sendRequest(senderId,receiverId), HttpStatus.OK);
    }
    @PostMapping("/respond/{requestId}")
    public ResponseEntity<?> respondToRequest(@PathVariable String requestId,@RequestParam String action){
        return new ResponseEntity<>(connectionService.respondToRequest(requestId,action),HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserConnections(@PathVariable String userId) {
        return new ResponseEntity<>(connectionService.getUserConnections(userId),HttpStatus.OK);
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<?> getPendingRequests(@PathVariable String userId) {
        return new ResponseEntity<>(connectionService.getPendingRequests(userId),HttpStatus.OK);
    }
}
