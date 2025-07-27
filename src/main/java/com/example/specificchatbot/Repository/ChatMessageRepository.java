package com.example.specificchatbot.Repository;

import com.example.specificchatbot.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomIdOrderByCreatedAt(Long chatRoomId);
}
