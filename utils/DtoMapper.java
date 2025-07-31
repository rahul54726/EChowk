package com.EChowk.EChowk.utils;

import com.EChowk.EChowk.Entity.Request;
import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.dto.RequestDto;
import com.EChowk.EChowk.dto.SkillDto;
import com.EChowk.EChowk.dto.SkillOfferDto;
import com.EChowk.EChowk.dto.UserDto;

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
        return SkillOfferDto.builder()
                .id(offer.getId())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .skillId(offer.getSkill().getId())
                .skillName(offer.getSkill().getName())
                .userId(offer.getUser().getId())
                .userName(offer.getUser().getName())
                .userAvatar(null) // Placeholder for future avatar support
                .availability(offer.getAvailability())
                .maxStudents(offer.getMaxStudents())
                .currentStudents(offer.getCurrentStudents())
                .status(offer.getStatus())
                .createdAt(offer.getCreatedAt().toString())
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
}
