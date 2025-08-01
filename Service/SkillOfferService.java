package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.SkillOfferCreationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillOfferService {

    private final SkillOfferRepo skillOfferRepo;
    private final UserRepo userRepo;
    private final SkillRepo skillRepo;

    // ✅ Create Offer
    public SkillOffer createOffer(SkillOfferCreationDto dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Skill skill = skillRepo.findById(dto.getSkillId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        SkillOffer offer = SkillOffer.builder()
                .user(user)
                .skill(skill)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .availability(dto.isAvailable())
                .status("ACTIVE")
                .currentStudents(0)
                .maxStudents(1)
                .createdAt(LocalDateTime.now())
                .build();

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
