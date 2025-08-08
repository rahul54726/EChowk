package com.EChowk.EChowk.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "connections")
public class Connection {
    @Id
    private String id;

    private String senderId;
    private String receiverId;
    private String status; // PENDING, ACCEPTED, DECLINED

    private LocalDateTime createdAt;
}
