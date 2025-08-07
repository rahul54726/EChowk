package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.SkillOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SkillOfferRepoCustom {
    Page<SkillOffer> findFilteredOffers(String skillName, String status, Boolean available, Pageable pageable);
}
