package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillOfferService {

    private final SkillOfferRepo skillOfferRepo;
    private final UserRepo userRepo;
    private final SkillRepo skillRepo;

    // ✅ Create Offer
    public SkillOffer createOffer(SkillOffer offer) {
        // Fetch referenced Skill and User
        User user = userRepo.findById(offer.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Skill skill = skillRepo.findById(offer.getSkill().getId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        offer.setUser(user);
        offer.setSkill(skill);
        offer.setAvailability(true);
        offer.setCreatedAt(LocalDateTime.now());

        if (offer.getMaxStudents() == 0) offer.setMaxStudents(1);
        offer.setCurrentStudents(0);
        offer.setStatus("ACTIVE");

        return skillOfferRepo.save(offer);
    }

    // ✅ Get all offers
    public List<SkillOffer> getAllOffers() {
        return skillOfferRepo.findAll();
    }

    // ✅ Get offers by user
    public List<SkillOffer> getOfferByUserId(String userId) {
        return skillOfferRepo.findByUserId(userId);
    }

    // ✅ Get available offers
    public List<SkillOffer> getAvailableOffers() {
        return skillOfferRepo.findByAvailability(true);
    }

    // ✅ Get available offers for a specific user
    public List<SkillOffer> getAvailableOffersByUser(String userId) {
        return skillOfferRepo.findByUserIdAndAvailability(userId, true);
    }
}
