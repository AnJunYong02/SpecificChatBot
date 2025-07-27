package com.example.specificchatbot.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private Long roomId; // 채팅방 ID
    private String sender; // user or ai
    private String content;
}
