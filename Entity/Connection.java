package com.EChowk.EChowk.Entity;

import com.EChowk.EChowk.enums.ConnectionStatus;
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
    private ConnectionStatus status; // PENDING, ACCEPTED, DECLINED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public void onCreate(){
        this.status = this.status == null ? ConnectionStatus.PENDING : this.status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }
    public  void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
