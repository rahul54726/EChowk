package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.SkillOffer;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SkillOfferRepoImpl implements SkillOfferRepoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<SkillOffer> findFilteredOffers(String skillName, String status, Boolean available, Pageable pageable) {
        Query query = new Query();

        if (skillName != null && !skillName.isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(skillName, "i"));
        }

        if (status != null && !status.isEmpty()) {
            query.addCriteria(Criteria.where("status").is(status));
        }

        if (available != null) {
            query.addCriteria(Criteria.where("availability").is(available));
        }

        long total = mongoTemplate.count(query, SkillOffer.class);
        query.with(pageable);
        List<SkillOffer> offers = mongoTemplate.find(query, SkillOffer.class);

        return new PageImpl<>(offers, pageable, total);
    }
}
