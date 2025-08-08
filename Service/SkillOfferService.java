package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.ReviewRepo;
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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillOfferService {

    private final SkillOfferRepo skillOfferRepo;
    private final UserRepo userRepo;
    private final SkillRepo skillRepo;
    private final ReviewRepo reviewRepo;

    /**
     * Creates a new SkillOffer and evicts cached offers
     */
    @CacheEvict(value = {"skillOffers", "userSkillOffers"}, allEntries = true)
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

    /**
     * Retrieves all skill offers (cached)
     */
    @Cacheable(value = "skillOffers")
    public List<SkillOfferDto> getAllOffers() {
        log.info("Fetching all skill offers from DB...");
        List<SkillOffer> offers = skillOfferRepo.findAll();
        return offers.stream().map(DtoMapper::toSkillOfferDto).collect(Collectors.toList());
    }

    /**
     * Get skill offers by a specific user
     */
    @Cacheable(value = "userSkillOffers", key = "#userId")
    public List<SkillOffer> getOfferByUserId(String userId) {
        return skillOfferRepo.findByUserId(userId);
    }

    /**
     * Get all available skill offers
     */
    public List<SkillOffer> getAvailableOffers() {
        return skillOfferRepo.findByAvailability(true);
    }

    /**
     * Get available skill offers by a specific user
     */
    public List<SkillOffer> getAvailableOffersByUser(String userId) {
        return skillOfferRepo.findByUserIdAndAvailability(userId, true);
    }

    /**
     * Delete a skill offer and related reviews. Clears cache.
     */
    @Transactional
    @CacheEvict(value = {"skillOffers", "userSkillOffers"}, allEntries = true)
    public void deleteSkillOffer(String offerId, String userId) {
        reviewRepo.deleteBySkillOfferId(offerId);
        skillOfferRepo.deleteById(offerId);
    }

    /**
     * Paginated and filtered skill offer list
     */
    public Page<SkillOfferDto> getFilteredOffers(int page, int size,
                                                 String skillName,
                                                 String status,
                                                 Boolean available) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<SkillOffer> offers = skillOfferRepo.findFilteredOffers(skillName, status, available, pageable);
        return offers.map(DtoMapper::toSkillOfferDto);
    }
    public Page<SkillOfferDto> searchSkillOffers(String keyword, Pageable pageable) {
        String lowered = keyword.toLowerCase();

        Page<SkillOffer> offers = skillOfferRepo.findAll(pageable);

        List<SkillOfferDto> filtered = offers.getContent().stream()
                .filter(offer ->
                        (offer.getTitle() != null && offer.getTitle().toLowerCase().contains(lowered)) ||
                                (offer.getDescription() != null && offer.getDescription().toLowerCase().contains(lowered)) ||
                                (offer.getSkill() != null && offer.getSkill().getName() != null &&
                                        offer.getSkill().getName().toLowerCase().contains(lowered))
                )
                .map(DtoMapper::toSkillOfferDto)
                .toList();

        return new PageImpl<>(filtered, pageable, offers.getTotalElements());
    }



}
