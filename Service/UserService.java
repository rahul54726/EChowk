package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.PasswordResetToken;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.*;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import com.EChowk.EChowk.dto.UserUpdateRequest;
import com.EChowk.EChowk.enums.Role;
import com.EChowk.EChowk.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.enums.ConnectionStatus;
import com.EChowk.EChowk.dto.UserProfileDto;
import com.EChowk.EChowk.utils.DtoMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final SkillRepo skillRepo;
    private final SkillOfferRepo skillOfferRepo;
    private final RequestRepo requestRepo;
    private final ReviewRepo reviewRepo;
    private final PasswordResetTokenRepo passwordResetTokenRepo;
    private final EmailService emailService;
    private final CloudinaryUploadService cloudinaryUploadService;
    private final PasswordEncoder passwordEncoder;
    private final ConnectionRepo connectionRepo;

    public User registerUser(User user) {
        Optional<User> existing = userRepo.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            log.error("Email Already Exists: {}", user.getEmail());
            throw new RuntimeException("Email Already Exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        User saved = userRepo.save(user);
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());
        return saved;
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User updateUser(User user) {
        User existing = userRepo.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existing.setName(user.getName());
        existing.setBio(user.getBio());
        existing.setLocation(user.getLocation());
        return userRepo.save(existing);
    }

    @Transactional
    public void deleteUser(String id) {
        skillOfferRepo.deleteByUserId(id);
        requestRepo.deleteByRequesterId(id);
        reviewRepo.deleteByReviewerId(id);
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
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setName(dto.getName());
        user.setBio(dto.getBio());
        user.setLocation(dto.getLocation());
        return userRepo.save(user);
    }

    public void initiatePasswordReset(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

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

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepo.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or expired token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepo.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        passwordResetTokenRepo.delete(resetToken);
    }

    public User uploadProfilePicture(String userId, MultipartFile file) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String imageUrl = cloudinaryUploadService.uploadProfilePicture(file, userId);
        user.setProfilePictureUrl(imageUrl);
        return userRepo.save(user);
    }

    /**
     * Get user profile data for profile page
     */
    public UserProfileDto getUserProfile(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return DtoMapper.toUserProfileDto(user);
    }

    /**
     * Get skills for a specific user
     */
    public List<Skill> getUserSkills(String userId) {
        return skillRepo.findByUserId(userId);
    }

    /**
     * Get skill offers for a specific user
     */
    public List<SkillOffer> getUserOffers(String userId) {
        return skillOfferRepo.findByUserId(userId);
    }

    /**
     * Get comprehensive user statistics for profile page
     */
    public Map<String, Object> getUserStats(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        int totalSkills = skillRepo.countByUserId(userId);
        int totalOffers = skillOfferRepo.findByUserId(userId).size();
        int totalStudents = skillOfferRepo.findByUserId(userId).stream()
                .mapToInt(SkillOffer::getCurrentStudents)
                .sum();
        int totalConnections = connectionRepo.findBySenderIdOrReceiverIdAndStatus(userId, userId, ConnectionStatus.ACCEPTED).size();
        
        return Map.of(
            "totalSkills", totalSkills,
            "totalOffers", totalOffers,
            "totalStudents", totalStudents,
            "totalConnections", totalConnections,
            "averageRating", user.getAverageRating(),
            "totalReviews", reviewRepo.countByReviewer_Id(userId)
        );
    }
}