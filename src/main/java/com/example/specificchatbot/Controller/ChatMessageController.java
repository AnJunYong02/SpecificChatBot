package com.example.specificchatbot.Controller;

import com.example.specificchatbot.Dto.ChatMessageDto;
import com.example.specificchatbot.Entity.ChatMessage;
import com.example.specificchatbot.Entity.ChatRoom;
import com.example.specificchatbot.Repository.ChatMessageRepository;
import com.example.specificchatbot.Repository.ChatRoomRepository;
import com.example.specificchatbot.Service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    // 사용자가 채팅방 들어왔을 때 호출되는 메서드 -> 이전 대화기록 불러오기
    @MessageMapping("/chat.enter")
    public void handleChatEnter(ChatMessageDto chatMessageDto) {
        Long roomId = chatRoomRepository.findById(chatMessageDto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."))
                .getId();;

        // 해딩 채팅방의 이전 메시지 불러오기
        var history = chatMessageRepository.findByChatRoomIdOrderByCreatedAt(roomId);

        // DTO 변환
        var dtoList = history.stream().map(message -> ChatMessageDto.builder()
                .roomId(roomId)
                .sender(message.getSender())
                .content(message.getContent())
                .build()
        ).toList();
//        System.out.println("dtoList = " + dtoList);

        // 클라이언트에게 이전 메시지 전송
        simpMessagingTemplate.convertAndSend("/topic/history/" + roomId, dtoList);
    }

    // 메시지 보낼 때 호출되는 메서드
    @MessageMapping("/chat.send") // 클라이언트가 /app/chat.send로 메시지를 보낼 때 이 메서드가 호출됨
    public void handleChatMessage(ChatMessageDto chatMessageDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId()).orElseThrow();

        // 1. 사용자 메시지 저장
        ChatMessage userMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(chatMessageDto.getSender())
                .content(chatMessageDto.getContent())
                .build();
        chatMessageRepository.save(userMessage);

        // 2. AI 응답 생성
        String aiResponse = chatMessageService.getAiResponse(chatRoom.getTopic(), chatMessageDto.getContent());

        // 3. AI 메시지 저장
        ChatMessage aiMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender("ai")
                .content(aiResponse)
                .build();
        chatMessageRepository.save(aiMessage);

        // 4. 사용자와 AI 메시지를 모두 전송
        simpMessagingTemplate.convertAndSend("/topic/chat/" + chatMessageDto.getRoomId(), chatMessageDto); // 사용자 메시지
        simpMessagingTemplate.convertAndSend("/topic/chat/" + chatMessageDto.getRoomId(), new ChatMessageDto() {{ // AI 메시지
            setRoomId(chatMessageDto.getRoomId());
            setSender("ai");
            setContent(aiResponse);
        }});
    }
}
