package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRepo extends MongoRepository<Skill, String> {

    List<Skill> findByUser(User user);

    List<Skill> findByUserAndType(User user, String type);

    List<Skill> findByUserId(String userId);

    int countByUserId(String userId);

    List<Skill> findByNameContainingIgnoreCase(String name);

    List<Skill> findByDescriptionContainingIgnoreCase(String description);

    Page<Skill> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String nameKeyword,
            String descriptionKeyword,
            Pageable pageable
    );
}
