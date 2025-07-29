package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.SkillRequest;
import com.EChowk.EChowk.Repository.SkillRequestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillRequestService {

    private final SkillRequestRepo skillRequestRepo;
    public SkillRequest createRequest(SkillRequest request){
        return skillRequestRepo.save(request);
    }
    public List<SkillRequest> getRequestByUserId(String userId){
        return skillRequestRepo.findByUserId(userId);
    }
    public List<SkillRequest> getAllRequest(){
        return skillRequestRepo.findAll();
    }
}
