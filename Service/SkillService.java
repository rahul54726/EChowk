package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Repository.SkillRepo;
import lombok.RequiredArgsConstructor;
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
}
