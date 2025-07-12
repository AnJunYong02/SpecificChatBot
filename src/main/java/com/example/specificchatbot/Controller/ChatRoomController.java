package com.example.specificchatbot.Controller;

import com.example.specificchatbot.Entity.ChatRoom;
import com.example.specificchatbot.Service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    //채팅방 생성
    @PostMapping()
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom);
    }

    //채팅방 하나 불러오기
    @GetMapping("/{id}")
    public ChatRoom findChatRoomById(@PathVariable Long id) {
        return chatRoomService.findChatRoomById(id);
    }

    //채팅방 전체 불러오기
    @GetMapping()
    public List<ChatRoom> findAll(){
        return chatRoomService.findAll();
    }

    //채팅방 수정
    @PutMapping("/{id}")
    public ChatRoom updateChatRoom(@PathVariable Long id, @RequestBody ChatRoom updatedChatRoom) {
        return chatRoomService.updateChatRoom(id, updatedChatRoom);
    }

    //채팅방 삭제
    @DeleteMapping("/{id}")
    public void deleteChatRoom(@PathVariable Long id){
        chatRoomService.deleteChatRoom(id);
    }
}
