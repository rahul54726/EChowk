package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Service.SkillOfferService;
import com.EChowk.EChowk.dto.SkillOfferCreationDto;
import com.EChowk.EChowk.dto.SkillOfferDto;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class SkillOfferController {

    private final SkillOfferService skillOfferService;

    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody SkillOfferCreationDto dto) {
        SkillOffer saved = skillOfferService.createOffer(dto);
        return new ResponseEntity<>(DtoMapper.toSkillOfferDto(saved), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOffersByUser(@PathVariable String userId){
        return new ResponseEntity<>(skillOfferService.getOfferByUserId(userId).stream()
                .map(DtoMapper::toSkillOfferDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }
//    @GetMapping
//    public ResponseEntity<?> getAllSkillOffers() {
//        List<SkillOfferDto> offers = skillOfferService.getAllOffers();
//        return new ResponseEntity<>(offers,HttpStatus.OK);
//    }
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableOffers(){
        return new ResponseEntity<>(skillOfferService.getAvailableOffers(),HttpStatus.OK);
    }
    @GetMapping("/users/{userId}/available")
    public ResponseEntity<?> getAvailableOfferByUser(@PathVariable String userId){
        return  new ResponseEntity<>(skillOfferService.getAvailableOffersByUser(userId),HttpStatus.OK);
    }
    @DeleteMapping("/{offerId}")
    public ResponseEntity<?> deleteOffer(@PathVariable String offerId,@RequestParam String userId){
        skillOfferService.deleteSkillOffer(offerId,userId);
        return new ResponseEntity<>("Skill Offer Deletele",HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Page<SkillOfferDto>> getAllOffers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String skillName,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Boolean available
    ) {
        Page<SkillOfferDto> offers = skillOfferService.getFilteredOffers(page, size, skillName, status, available);
        return ResponseEntity.ok(offers);
    }
}
