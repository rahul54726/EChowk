package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.SkillOffer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillOfferRepo extends MongoRepository<SkillOffer, String> {
    List<SkillOffer> findByUserId(String userId);
    List<SkillOffer> findByAvailability(boolean availability);
    List<SkillOffer> findByUserIdAndAvailability(String userId, boolean availability);
    long countByUser_Id(String userId);
}
