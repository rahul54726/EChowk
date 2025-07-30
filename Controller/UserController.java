package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Service.UserService;
import com.EChowk.EChowk.dto.UserDto;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User created = userService.registerUser(user);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.warn("Something went wrong while registering user", e);
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new ResponseEntity<>(DtoMapper.toUserDto(user), HttpStatus.OK);
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new ResponseEntity<>(DtoMapper.toUserDto(user), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
