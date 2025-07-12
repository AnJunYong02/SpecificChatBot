package com.example.specificchatbot.Service;

import com.example.specificchatbot.Entity.ChatRoom;
import com.example.specificchatbot.Repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    //채팅방 생성
    public ChatRoom createChatRoom(ChatRoom chatRoom){
        return chatRoomRepository.save(chatRoom);
    }

    //채팅방 하나 불러오기
    public ChatRoom findChatRoomById(Long id){
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat room not found with id: " + id));
    }

    //채팅방 전체 불러오기
    public List<ChatRoom> findAll(){
        return chatRoomRepository.findAll();
    }

    //채팅방 수정
    public ChatRoom updateChatRoom(Long id, ChatRoom updatedChatRoom) {
        return chatRoomRepository.findById(id).map(room -> {
            room.setRoomName(updatedChatRoom.getRoomName());
            room.setUserName(updatedChatRoom.getUserName());
            room.setAiName(updatedChatRoom.getAiName());
            room.setTopic(updatedChatRoom.getTopic());
            return chatRoomRepository.save(room);
        }).orElseThrow(() -> new RuntimeException("Chat room not found with id: " + id));
    }

    //채팅방 삭제
    public void deleteChatRoom(Long id){
        chatRoomRepository.deleteById(id);
    }
}
