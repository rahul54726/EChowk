package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Service.SkillOfferService;
import com.EChowk.EChowk.dto.SkillOfferDto;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/offers")
@RequiredArgsConstructor
public class SkillOfferController {

    private final SkillOfferService skillOfferService;

    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody SkillOffer offer){
        SkillOffer saved = skillOfferService.createOffer(offer);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOffersByUser(@PathVariable String userId){
        return new ResponseEntity<>(skillOfferService.getOfferByUserId(userId).stream()
                .map(DtoMapper::toSkillOfferDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllSkillOffers() {
        return new ResponseEntity<>(skillOfferService.getAllOffers().stream()
                .map(DtoMapper::toSkillOfferDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableOffers(){
        return new ResponseEntity<>(skillOfferService.getAvailableOffers(),HttpStatus.OK);
    }
    @GetMapping("/users/{userId}/available")
    public ResponseEntity<?> getAvailableOfferByUser(@PathVariable String userId){
        return  new ResponseEntity<>(skillOfferService.getAvailableOffersByUser(userId),HttpStatus.OK);
    }
}
