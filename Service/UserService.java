package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.Repository.SkillRequestRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class UserService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private SkillOfferRepo skillOfferRepo;
    @Autowired
    private SkillRequestRepo requestRepo;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    public User registeruser(User user){
        Optional<User> existing = userRepo.findByEmail(user.getEmail());
        if(existing.isPresent()){
            log.error("Email Already Exists.");
            throw new RuntimeException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public Optional<User> getUserById(String id){
        return userRepo.findById(id);
    }
    public Optional<User> getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public User updateUser(User user){
        return userRepo.save(user);
    }
    public void deleteUser(String id){
         userRepo.deleteById(id);
    }
    public DashboardStatsDto getDashoardStats(String userId){
        int totalSKills = skillRepo.countByUserId(userId);
        int totalOffers = skillOfferRepo.findByUserId(userId).size();
        int totalRequest = requestRepo.findByUserId(userId).size();
        double averageRating = userRepo.findById(userId)
                .map(User::getAverageRating).orElse(0.0);
        return DashboardStatsDto.builder()
                .totalSkills(totalSKills)
                .totalOffers(totalOffers)
                .totalRequests(totalRequest)
                .averageRating(averageRating)
                .build();
    }
}
