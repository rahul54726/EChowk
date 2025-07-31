package com.EChowk.EChowk.Controller;

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
    public ResponseEntity<List<SkillDto>> getAllSkills() {
        List<SkillDto> skillDtos = skillService.getAllSkills().stream()
                .map(DtoMapper::toSkillDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(skillDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SkillDto> createSkill(@RequestBody SkillDto skillDto) {
        SkillDto created = skillService.createSkill(skillDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
