package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.SkillOfferCreationDto;
import com.EChowk.EChowk.dto.SkillOfferDto;
import com.EChowk.EChowk.exception.ResourceNotFoundException;
import com.EChowk.EChowk.utils.DtoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillOfferService {

    private final SkillOfferRepo skillOfferRepo;
    private final UserRepo userRepo;
    private final SkillRepo skillRepo;

    // ✅ Create a new Skill Offer and evict cached offers
    @CacheEvict(value = "skillOffers", allEntries = true)
    public SkillOffer createOffer(SkillOfferCreationDto dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Skill skill = skillRepo.findById(dto.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

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

    // ✅ Fetch all offers and cache the result
    @Cacheable(value = "skillOffers")
    public List<SkillOfferDto> getAllOffers() {
        log.info("Fetching skill offers from database...");
        List<SkillOffer> offers = skillOfferRepo.findAll();
        return offers.stream().map(DtoMapper::toSkillOfferDto).collect(Collectors.toList());
    }

    // ✅ Get offers by user
    public List<SkillOffer> getOfferByUserId(String userId) {
        return skillOfferRepo.findByUserId(userId);
    }

    // ✅ Get only available offers
    public List<SkillOffer> getAvailableOffers() {
        return skillOfferRepo.findByAvailability(true);
    }

    // ✅ Get available offers created by a specific user
    public List<SkillOffer> getAvailableOffersByUser(String userId) {
        return skillOfferRepo.findByUserIdAndAvailability(userId, true);
    }
}