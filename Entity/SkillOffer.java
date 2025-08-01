package com.EChowk.EChowk.Entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "skill_offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillOffer {

    @Id
    private String id;

    private String title;

    @DBRef
    private User user;

    @DBRef
    private Skill skill;

    private String description;

    private Boolean availability = true;

    private Integer maxStudents = 1;

    private Integer currentStudents = 0;

    private String status = "OPEN";

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    public boolean isAvailability() {
        return this.availability;
    }

    public void setAvailable(boolean b) {
        availability = b;
    }
}
