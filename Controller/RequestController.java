package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.Request;
import com.EChowk.EChowk.Service.RequestService;
import com.EChowk.EChowk.dto.RequestCreationDto;
import com.EChowk.EChowk.dto.RequestDto;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<RequestDto> createRequest(@RequestBody RequestCreationDto dto) {
        Request request = requestService.createRequest(dto);
        return ResponseEntity.ok(DtoMapper.toRequestDto(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RequestDto>> getRequestsByUser(@PathVariable String userId) {
        List<RequestDto> requests = requestService.getRequestsByUser(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/offer/{offerId}")
    public ResponseEntity<List<RequestDto>> getRequestsByOffer(@PathVariable String offerId) {
        List<RequestDto> requests = requestService.getRequestsByOffer(offerId);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{requestId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable String requestId, @RequestParam String status) {
        requestService.updateRequestStatus(requestId, status);
        return ResponseEntity.ok("Status updated to: " + status);
    }
}
