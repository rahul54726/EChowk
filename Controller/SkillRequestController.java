package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.SkillRequest;
import com.EChowk.EChowk.Service.SkillRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class SkillRequestController {

    private final SkillRequestService skillRequestService;

    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody SkillRequest request){
        return new ResponseEntity<>(skillRequestService.createRequest(request), HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<?> getRequestByUser(@PathVariable String userId){
        return new ResponseEntity<>(skillRequestService.getRequestByUserId(userId), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllRequests(){
        return new ResponseEntity<>(skillRequestService.getAllRequest(),HttpStatus.OK);
    }

}


