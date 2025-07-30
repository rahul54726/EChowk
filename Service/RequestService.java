package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Request;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.RequestRepo;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.RequestCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    @Autowired
    private final RequestRepo requestRepo;
    @Autowired
    private final SkillOfferRepo skillOfferRepo;
    @Autowired
    private final UserRepo userRepo;

    // ✅ Create a new request
    public Request createRequest(RequestCreationDto dto) {
        SkillOffer offer = skillOfferRepo.findById(dto.getSkillOfferId())
                .orElseThrow(() -> new RuntimeException("Skill Offer not found"));

        User requester = userRepo.findById(dto.getRequesterId())
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        Request request = new Request();
        request.setSkillOffer(offer);
        request.setRequester(requester);
        request.setStatus("PENDING");

        return requestRepo.save(request);
    }

    // ✅ Get all requests made by a specific user
    public List<Request> getRequestsByUser(String userId) {
        return requestRepo.findByRequesterId(userId);
    }

    // ✅ Get all requests for a specific skill offer (for owner to view)
    public List<Request> getRequestsByOffer(String offerId) {
        return requestRepo.findBySkillOfferId(offerId);
    }

    // ✅ Update the status of a request (ACCEPTED or REJECTED)
    public void updateRequestStatus(String requestId, String status) {
        Request request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status.toUpperCase());
        requestRepo.save(request);
    }
}
