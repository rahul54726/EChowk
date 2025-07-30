package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.Request;
import com.EChowk.EChowk.Service.RequestService;
import com.EChowk.EChowk.Service.SkillOfferService;
import com.EChowk.EChowk.Service.UserService;
import com.EChowk.EChowk.dto.RequestCreationDto;
import com.EChowk.EChowk.dto.RequestDto;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestController {
//    @Autowired
//    private final RequestService requestService;
//
//    @PostMapping
//    public ResponseEntity<?> createREequest(@RequestBody Request request){
//        return new ResponseEntity<>(requestService.createRequest(request), HttpStatus.OK);
//    }
//
//    @GetMapping("/sent/{userId}")
//    public  ResponseEntity<?> getRequestByUserId(@PathVariable String userId){
//        return new ResponseEntity<>(requestService.getRequestsByUserId(userId),HttpStatus.OK);
//    }
//    @GetMapping("/received/{userId}")
//    public ResponseEntity<?> getIncomingRequests(@PathVariable String userId){
//        return new ResponseEntity<>(requestService.getIncomingRequestsForUsers(userId),HttpStatus.OK);
//    }
//    @PutMapping("/{requestId}/status")
//    public ResponseEntity<?> updateStatus(@PathVariable String requestId,@RequestParam String status){
//        return new ResponseEntity<>(requestService.updateRequestStatus(requestId,status),HttpStatus.OK);
//    }
private final RequestService requestService;

    // ✅ Create a new request
    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody RequestCreationDto dto) {
        Request created = requestService.createRequest(dto);
        return new ResponseEntity(DtoMapper.toRequestDto(created), HttpStatus.OK);
    }

    // ✅ Get all requests for a given user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRequestsByUser(@PathVariable String userId) {
        List<Request> requests = requestService.getRequestsByUser(userId);
        List<RequestDto> dtos = requests.stream()
                .map(DtoMapper::toRequestDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ✅ Get all requests for a given skill offer (for owner of offer)
    @GetMapping("/offer/{offerId}")
    public ResponseEntity<List<RequestDto>> getRequestsByOffer(@PathVariable String offerId) {
        List<Request> requests = requestService.getRequestsByOffer(offerId);
        List<RequestDto> dtos = requests.stream()
                .map(DtoMapper::toRequestDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ✅ Accept or Reject a request (optional)
    @PutMapping("/{requestId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable String requestId,
            @RequestParam String status // ACCEPTED or REJECTED
    ) {
        requestService.updateRequestStatus(requestId, status);
        return ResponseEntity.ok("Status updated to " + status);
    }
}
