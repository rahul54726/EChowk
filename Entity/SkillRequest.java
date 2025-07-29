package com.EChowk.EChowk.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class SkillRequest {
    @Id
    private String id;

    @DBRef
    private  User user;
    @DBRef
    private Skill skill;

    private String note;
}
