package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.PasswordResetToken;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.*;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import com.EChowk.EChowk.dto.UserUpdateRequest;
import com.EChowk.EChowk.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final SkillRepo skillRepo;
    private final PasswordEncoder passwordEncoder;
    private final SkillOfferRepo skillOfferRepo;
    private final RequestRepo requestRepo;
    private final PasswordResetTokenRepo passwordResetTokenRepo;
    private final EmailService emailService;

    public User registerUser(User user) {
        Optional<User> existing = userRepo.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            log.error("Email Already Exists: {}", user.getEmail());
            throw new RuntimeException("Email Already Exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());
        return userRepo.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }

    public DashboardStatsDto getDashboardStats(String userId) {
        int totalSkills = skillRepo.countByUserId(userId);
        int totalOffers = skillOfferRepo.findByUserId(userId).size();
        int totalRequests = requestRepo.findByRequester_Id(userId).size();
        double averageRating = userRepo.findById(userId)
                .map(User::getAverageRating)
                .orElse(0.0);

        return DashboardStatsDto.builder()
                .totalSkills(totalSkills)
                .totalOffers(totalOffers)
                .totalRequests(totalRequests)
                .averageRating(averageRating)
                .build();
    }

    public void seedDemoUser() {
        String demoEmail = "demo@skillhub.com";
        if (userRepo.findByEmail(demoEmail).isPresent()) return;

        User demoUser = User.builder()
                .name("Demo User")
                .email(demoEmail)
                .password(passwordEncoder.encode("demo123"))
                .bio("I'm a demo user for testing!")
                .location("Nowhere")
                .averageRating(4.5)
                .role(Role.USER)
                .build();

        userRepo.save(demoUser);
    }

    public void seedAdminUser() {
        String adminEmail = "admin@skillhub.com";
        if (userRepo.findByEmail(adminEmail).isPresent()) return;

        User admin = User.builder()
                .name("Admin")
                .email(adminEmail)
                .password(passwordEncoder.encode("admin123"))
                .bio("I am Administrator")
                .location("Headquarters")
                .role(Role.ADMIN)
                .build();

        userRepo.save(admin);
    }

    public User updateProfile(UserUpdateRequest dto, String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(dto.getName());
        user.setBio(dto.getBio());
        user.setLocation(dto.getLocation());
        return userRepo.save(user);
    }

    public void initiatePasswordReset(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(30);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .email(email)
                .token(token)
                .expiryDate(expiry)
                .build();

        passwordResetTokenRepo.save(resetToken);

        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(email, resetLink);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or Expired Token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepo.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        passwordResetTokenRepo.delete(resetToken);
    }
}
