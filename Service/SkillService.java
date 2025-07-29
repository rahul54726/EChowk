package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private UserRepo userRepo;
    public Skill addSkill(String userID,Skill skill){
        User user = userRepo.findByEmail(userID).orElseThrow(() -> new RuntimeException("User Not Found"));
        skill.setUser(user);
        return skillRepo.save(skill);
    }
    public List<Skill> getSkillByUser(String userID){
        User user = userRepo.findByEmail(userID).orElseThrow(() -> new RuntimeException("user Not Found"));
        return skillRepo.findByUser(user);
    }
    public List<Skill> getSkillByType(String userID,String type){
        User user = userRepo.findByEmail(userID).orElseThrow(() -> new RuntimeException("User Not Found"));
        return skillRepo.findByUserAndType(user,type.toUpperCase());
    }
}
