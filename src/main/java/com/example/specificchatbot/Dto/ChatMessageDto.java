package com.example.specificchatbot.Dto;

import lombok.Data;

@Data
public class ChatMessageDto {
    private Long roomId; // 채팅방 ID
    private String sender; // user or ai
    private String content;
}
