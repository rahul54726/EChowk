package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.dto.SkillDto;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepo skillRepo;

    public List<SkillDto> getAllSkills() {
        return skillRepo.findAll()
                .stream()
                .map(DtoMapper::toSkillDto)
                .collect(Collectors.toList());
    }

    public Skill saveSkill(Skill skill) {
        return skillRepo.save(skill);
    }
}
