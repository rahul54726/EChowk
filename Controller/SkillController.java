package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.Service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skills")
public class SkillController {
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private UserRepo userRepo;
    private final SkillService skillService;
    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/add/{email}")
    public ResponseEntity<?> addSkill(@PathVariable String email, @RequestBody Skill skill){
        Skill savedSkill = skillService.addSkill(email,skill);
        return new ResponseEntity<>(savedSkill,HttpStatus.OK);
    }
    @GetMapping("user/{userID}")
    public ResponseEntity<?> getSkillsByUser(@PathVariable String userID){
        List<Skill> skills = skillService.getSkillByUser(userID);
        return new ResponseEntity<>(skills,HttpStatus.OK);
    }
    @GetMapping("/user/{userID}/type/{type}")
    public ResponseEntity<?> getSkillsByType(@PathVariable String userID, @PathVariable String type) {
        List<Skill> skills = skillService.getSkillByType(userID,type);
        return new ResponseEntity<>(skills,HttpStatus.OK);
    }
}
