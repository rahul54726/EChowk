package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RequestRepo extends MongoRepository<Request, String> {
    List<Request> findByRequester_Id(String requesterId);
    List<Request> findBySkillOffer_Id(String skillOfferId);
}
