package com.EChowk.EChowk.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {
    @Id
    private String id;

    @DBRef
    private User requester;

    @DBRef
    private  SkillOffer skillOffer;

    private  String status;
}
