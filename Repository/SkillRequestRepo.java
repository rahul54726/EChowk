package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.SkillRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRequestRepo extends MongoRepository<SkillRequest,String> {
    List<SkillRequest> findByUserID(String userId);
}
