package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Request;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.RequestRepo;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.RequestCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepo requestRepo;
    private final SkillOfferRepo skillOfferRepo;
    private final UserRepo userRepo;
    private final EmailService emailService;
    // ✅ Create a new request
    public Request createRequest(RequestCreationDto dto) {
        // 1. Get the skill offer by ID
        SkillOffer offer = skillOfferRepo.findById(dto.getSkillOfferId())
                .orElseThrow(() -> new RuntimeException("Skill Offer not found"));

        // 2. Get the user who is requesting
        User requester = userRepo.findById(dto.getRequesterId())
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        // 3. Save the request
        Request request = new Request();
        request.setSkillOffer(offer);
        request.setRequester(requester);
        request.setStatus("PENDING");
        Request saved = requestRepo.save(request);

        // 4. Send notification to the tutor via email
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



    // ✅ Get all requests made by a specific user
    public List<Request> getRequestsByUser(String userId) {
        return requestRepo.findByRequester_Id(userId);  // This must match the method name in your repo
    }

    // ✅ Get all requests for a specific skill offer (for offer owner)
    public List<Request> getRequestsByOffer(String offerId) {
        return requestRepo.findBySkillOffer_Id(offerId);
    }

    // ✅ Update the status of a request (ACCEPTED / REJECTED)
    public void updateRequestStatus(String requestId, String status) {
        Request request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(status.toUpperCase());
        requestRepo.save(request);

        // ✉️ Notify requester
        User requester = request.getRequester();
        SkillOffer offer = request.getSkillOffer();
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
