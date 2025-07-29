package com.EChowk.EChowk.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "skill_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillRequest {
    @Id
    private String id;

    @DBRef
    @JsonIgnore
    private  User user;
    @DBRef
    @JsonIgnore
    private Skill skill;

    private String note;
    private boolean available;
}
