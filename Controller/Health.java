package com.EChowk.EChowk.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
