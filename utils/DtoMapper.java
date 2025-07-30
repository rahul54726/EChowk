package com.EChowk.EChowk.utils;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.dto.SkillDto;
import com.EChowk.EChowk.dto.SkillOfferDto;
import com.EChowk.EChowk.dto.UserDto;

public class DtoMapper {
    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .location(user.getLocation())
                .averageRating(user.getAverageRating())
                .build();
    }
    public static SkillDto toSkillDto(Skill skill){
        return SkillDto.builder()
                .id(skill.getId())
                .name(skill.getName())
                .type(skill.getType())
                .build();
    }
    public static SkillOfferDto toSkillOfferDto(SkillOffer offer) {
        return SkillOfferDto.builder()
                .id(offer.getId())
                .skill(toSkillDto(offer.getSkill()))
                .user(toUserDto(offer.getUser()))
                .available(offer.isAvailable())
                .build();
    }
}
