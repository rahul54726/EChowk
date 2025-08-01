package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.RequestRepo;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import com.EChowk.EChowk.dto.UserUpdateRequest;
import com.EChowk.EChowk.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepo userRepo;
    private final SkillRepo skillRepo;
    private final PasswordEncoder passwordEncoder;
    private final SkillOfferRepo skillOfferRepo;
    private final RequestRepo requestRepo;

    @Autowired
    public UserService(
            UserRepo userRepo,
            SkillRepo skillRepo,
            PasswordEncoder passwordEncoder,
            SkillOfferRepo skillOfferRepo,
            RequestRepo requestRepo
    ) {
        this.userRepo = userRepo;
        this.skillRepo = skillRepo;
        this.passwordEncoder = passwordEncoder;
        this.skillOfferRepo = skillOfferRepo;
        this.requestRepo = requestRepo;
    }
    @Autowired
    private EmailService emailService;
    public User registerUser(User user) {
        Optional<User> existing = userRepo.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            log.error("Email Already Exists: {}", user.getEmail());
            throw new RuntimeException("Email Already Exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        emailService.sendWelcomeEmail(user.getEmail(),user.getName());
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
        int totalRequests = requestRepo.findByRequester_Id(userId).size(); // <-- Ensure this method exists
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
        if (userRepo.findByEmail(demoEmail).isPresent()) {
            return; // Already exists, no need to seed again
        }

        User demoUser = User.builder()
                .name("Demo User")
                .email(demoEmail)
                .password(passwordEncoder.encode("demo123"))
                .bio("I'm a demo user for testing!")
                .location("Nowhere")
                .averageRating(4.5)
                .build();

        userRepo.save(demoUser);
    }
    public void seedAdminUser(){
        String adminEmail = "admin@skillhub.com";
        if(userRepo.findByEmail(adminEmail).isPresent()) return;
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
}
