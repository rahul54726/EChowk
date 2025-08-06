package com.EChowk.EChowk.utils;

import com.EChowk.EChowk.Entity.*;
import com.EChowk.EChowk.dto.*;

import java.time.LocalDateTime;

public class DtoMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) return null;

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .location(user.getLocation())
                .averageRating(user.getAverageRating())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }

    public static SkillDto toSkillDto(Skill skill) {
        if (skill == null) return null;

        return SkillDto.builder()
                .id(skill.getId())
                .name(skill.getName())
                .type(skill.getType())
                .build();
    }

    public static SkillOfferDto toSkillOfferDto(SkillOffer offer) {
        User user = offer.getUser();
        Skill skill = offer.getSkill();

        return SkillOfferDto.builder()
                .id(offer.getId())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .skillId(skill != null ? skill.getId() : null)
                .skillName(skill != null ? skill.getName() : "Unknown Skill")
                .userId(user != null ? user.getId() : null)
                .userName(user != null ? user.getName() : "Unknown User")
                .userAvatar(null)
                .availability(offer.getAvailability())
                .maxStudents(offer.getMaxStudents())
                .currentStudents(offer.getCurrentStudents())
                .status(offer.getStatus())
                .createdAt(offer.getCreatedAt() != null ? offer.getCreatedAt().toString() : null)
                .averageRating(offer.getAverageRating())
                .numReviews(offer.getNumReviews())
                .build();
    }



    public static SkillOffer toSkillOffer(SkillOfferDto dto) {
        if (dto == null) return null;

        return SkillOffer.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .availability(dto.isAvailability())
                .maxStudents(dto.getMaxStudents())
                .currentStudents(dto.getCurrentStudents())
                .status(dto.getStatus())
                .createdAt(LocalDateTime.now()) // default to now, or override if needed
                .build();
    }

    public static RequestDto toRequestDto(Request request) {
        if (request == null) return null;
        return RequestDto.builder()
                .id(request.getId())
                .status(request.getStatus())
                .requester(toUserDto(request.getRequester()))
                .skillOffer(toSkillOfferDto(request.getSkillOffer()))
                .build();
    }
    public static ReviewDto toReviewDto(Review review){
        return ReviewDto.builder()
                .id(review.getId())
                .reviewerId(review.getReviewer().getId())
                .reviewerName(review.getReviewer().getName())
                .skillOfferId(review.getSkillOffer().getId())
                .rating(review.getRating())
                .comment(review.getContent())
                .createdAt(review.getCreatedAt().toString())
                .build();

    }
}
