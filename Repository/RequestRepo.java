package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RequestRepo extends MongoRepository<Request,String> {
    List<Request> findByRequesterId(String requesterId);
    List<Request> findBySkillOfferId(String offerId);
}
