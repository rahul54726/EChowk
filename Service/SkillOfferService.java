package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillOfferService {
    @Autowired
    private final SkillOfferRepo skillOfferRepo;
    public SkillOffer createOffer(SkillOffer offer){
        return skillOfferRepo.save(offer);
    }
    public List<SkillOffer> getOfferByUserId(String userId){
        return skillOfferRepo.findByUserId(userId);
    }
    public List<SkillOffer> getAllOffers(){
        return skillOfferRepo.findAll();
    }
}
