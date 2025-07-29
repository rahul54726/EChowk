package com.EChowk.EChowk.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "skill_offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillOffer {
    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private  Skill skill;

    private String description;
    private boolean available = true;
}
