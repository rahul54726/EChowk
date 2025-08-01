package com.EChowk.EChowk.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class Health {
    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/test")
    public ResponseEntity<?> getSecureStats(){
        return new ResponseEntity<>("Only Admins Can Access that",HttpStatus.OK);
    }
}
