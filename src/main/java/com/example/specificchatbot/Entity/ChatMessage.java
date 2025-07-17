package com.example.specificchatbot.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "chat_room_id", nullable = false)
    @Getter @Setter
    private ChatRoom chatRoom;

    @Getter @Setter private String sender; // "user" or "ai"
    @Getter @Setter private String content;
    @Getter @Setter private LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}
