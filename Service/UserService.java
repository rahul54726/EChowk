package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

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
}
