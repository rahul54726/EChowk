package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Request;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.RequestRepo;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.RequestCreationDto;
import com.EChowk.EChowk.dto.RequestDto;
import com.EChowk.EChowk.exception.ResourceNotFoundException;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepo requestRepo;
    private final SkillOfferRepo skillOfferRepo;
    private final UserRepo userRepo;
    private final EmailService emailService;

    // ✅ Create a new request
    @Transactional
    public Request createRequest(RequestCreationDto dto) {
        SkillOffer offer = skillOfferRepo.findById(dto.getSkillOfferId())
                .orElseThrow(() -> new RuntimeException("Skill Offer not found"));

        User requester = userRepo.findById(dto.getRequesterId())
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        Request request = new Request();
        request.setSkillOffer(offer);
        request.setRequester(requester);
        request.setStatus("PENDING");
        Request saved = requestRepo.save(request);

        // Notify tutor
        User tutor = offer.getUser();
        if (tutor != null && tutor.getEmail() != null) {
            String subject = "New Skill Request Received!";
            String message = "<p>Hello " + tutor.getName() + ",</p>" +
                    "<p>You have received a new request for your skill offer: <b>" + offer.getTitle() + "</b>.</p>" +
                    "<p>Requester: <b>" + requester.getName() + "</b> (" + requester.getEmail() + ")</p>" +
                    "<p>Please log in to SkillHub to accept or reject the request.</p><br>" +
                    "<p>Regards,<br>SkillHub Team</p>";
            emailService.sendEmail(tutor.getEmail(), subject, message);
        }

        return saved;
    }

    public List<RequestDto> getRequestsByUser(String userId) {
        List<Request> requests = requestRepo.findByRequester_Id(userId);
        return requests.stream().map(DtoMapper::toRequestDto).collect(Collectors.toList());
    }

    public List<RequestDto> getRequestsByOffer(String offerId) {
        List<Request> requests = requestRepo.findBySkillOffer_Id(offerId);
        return requests.stream().map(DtoMapper::toRequestDto).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "skillOffers", allEntries = true)
    public void updateRequestStatus(String requestId, String status) {
        Request request = requestRepo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        request.setStatus(status.toUpperCase());
        requestRepo.save(request);

        SkillOffer offer = request.getSkillOffer();

        if (status.equalsIgnoreCase("ACCEPTED")) {
            offer.setCurrentStudents(offer.getCurrentStudents() + 1);

            if (offer.getCurrentStudents() >= offer.getMaxStudents()) {
                offer.setAvailability(false);
            }
            skillOfferRepo.save(offer);
        }

        User requester = request.getRequester();
        String tutorName = offer.getUser().getName();
        String subject = "Your Request has been " + status.toUpperCase();
        String message = String.format(
                "Hi %s,\n\nYour request for the skill '%s' offered by %s has been %s.\n\nThanks for using EChowk!",
                requester.getName(),
                offer.getSkill().getName(),
                tutorName,
                status.toUpperCase()
        );
        emailService.sendEmail(requester.getEmail(), subject, message);
    }
}