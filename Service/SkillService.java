package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.dto.SkillDto;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepo skillRepo;
    public List<Skill> getAllSkills() {
        return skillRepo.findAll();
    }

    public Skill saveSkill(Skill skill) {
        return skillRepo.save(skill);
    }

    public Page<SkillDto> searchSkills(String keyword, Pageable pageable) {
        return skillRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        keyword, keyword, pageable)
                .map(DtoMapper::toSkillDto);
    }
}
