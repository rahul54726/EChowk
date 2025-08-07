package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.SkillOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SkillOfferRepo extends MongoRepository<SkillOffer, String> ,SkillOfferRepoCustom{

    List<SkillOffer> findByUserId(String userId);

    List<SkillOffer> findByAvailability(boolean availability);

    List<SkillOffer> findByUserIdAndAvailability(String userId, boolean availability);

    long countByUser_Id(String userId);

    void deleteByUserId(String id);

}