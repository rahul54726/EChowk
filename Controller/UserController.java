package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.Service.JwtService;
import com.EChowk.EChowk.Service.UserService;
import com.EChowk.EChowk.dto.UserDto;
import com.EChowk.EChowk.dto.UserProfileDto;
import com.EChowk.EChowk.dto.UserUpdateRequest;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.dto.SkillDto;
import com.EChowk.EChowk.dto.SkillOfferDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;
    private final JwtService jwtService;

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
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(DtoMapper.toUserDto(user));
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(DtoMapper.toUserDto(user));
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String userId) {
        UserProfileDto profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable String id,
            @RequestHeader("Authorization") String token) {

        String jwt = token.substring(7);
        String currentUserId = jwtService.extractUserId(jwt);
        String currentUserRole = jwtService.extractUserRole(jwt); // You'll need this method in JwtService

        // If not admin, ensure they can delete only themselves
        if (!currentUserRole.equals("ADMIN") && !currentUserId.equals(id)) {
            throw new AccessDeniedException("You can only delete your own account.");
        }

        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }



    @PutMapping("/update")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserUpdateRequest request, @RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User updatedUser = userService.updateProfile(request, user.getId());
        return ResponseEntity.ok(DtoMapper.toUserDto(updatedUser));
    }

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<UserDto> uploadProfilePicture(@RequestHeader("Authorization") String token,
                                                        @RequestParam("file") MultipartFile file) {
        String jwt = token.substring(7);
        String userId = jwtService.extractUserId(jwt);
        User updatedUser = userService.uploadProfilePicture(userId, file);
        return ResponseEntity.ok(DtoMapper.toUserDto(updatedUser));
    }

    @GetMapping("/{userId}/skills")
    public ResponseEntity<List<SkillDto>> getUserSkills(@PathVariable String userId) {
        List<Skill> skills = userService.getUserSkills(userId);
        List<SkillDto> skillDtos = skills.stream().map(DtoMapper::toSkillDto).collect(Collectors.toList());
        return ResponseEntity.ok(skillDtos);
    }

    @GetMapping("/{userId}/offers")
    public ResponseEntity<List<SkillOfferDto>> getUserOffers(@PathVariable String userId) {
        List<SkillOffer> offers = userService.getUserOffers(userId);
        List<SkillOfferDto> offerDtos = offers.stream().map(DtoMapper::toSkillOfferDto).collect(Collectors.toList());
        return ResponseEntity.ok(offerDtos);
    }

    @GetMapping("/{userId}/stats")
    public ResponseEntity<Map<String, Object>> getUserStats(@PathVariable String userId) {
        Map<String, Object> stats = userService.getUserStats(userId);
        return ResponseEntity.ok(stats);
    }
}