package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.RequestRepo;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User registerUser(User user) {
        Optional<User> existing = userRepo.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            log.error("Email Already Exists: {}", user.getEmail());
            throw new RuntimeException("Email Already Exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
}
