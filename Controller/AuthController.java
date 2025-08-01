package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.Service.JwtService;
import com.EChowk.EChowk.auth.LoginRequest;
import com.EChowk.EChowk.auth.LoginResponse;
import com.EChowk.EChowk.dto.AuthenticationRequest;
import com.EChowk.EChowk.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(String.valueOf(user.getRole()))  // <-- Return Role here
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
