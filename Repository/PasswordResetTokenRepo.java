package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PasswordResetTokenRepo extends MongoRepository<PasswordResetToken,String> {
    Optional<PasswordResetToken> findByToken(String s);
}
