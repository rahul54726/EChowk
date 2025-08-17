package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo  extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
}