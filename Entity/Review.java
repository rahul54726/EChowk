package com.EChowk.EChowk.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    private String id;

    private String content;
    private int rating;
    @DBRef
    private User reviewer;

    @DBRef
    private  SkillOffer skillOffer;
    private LocalDateTime createdAt = LocalDateTime.now();
}
