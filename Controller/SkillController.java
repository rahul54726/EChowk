package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Service.SkillService;
import com.EChowk.EChowk.dto.SkillDto;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<?> getAllSkills() {
        List<SkillDto> skills = skillService.getAllSkills()
                .stream()
                .map(DtoMapper::toSkillDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SkillDto> createSkill(@RequestBody SkillDto skillDto) {
        Skill saved = skillService.saveSkill(
                Skill.builder()
                        .name(skillDto.getName())
                        .type(skillDto.getType())
                        .build()
        );
        return new ResponseEntity<>(DtoMapper.toSkillDto(saved), HttpStatus.CREATED);
    }
}
