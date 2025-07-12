package com.example.specificchatbot.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@MessageMapping("/chat.send") // 클라이언트가 /app/chat.send로 메시지를 보낼 때 이 메서드가 호출됨
@SendTo("/topic/chat/{roomId}") // 메시지를 구독하는 클라이언트에게 전송
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // 구독 주소
        config.setApplicationDestinationPrefixes("/app"); // 메시지 전송 주소
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 웹소캣 연결 엔드포인트
                .setAllowedOriginPatterns("*")
                .withSockJS(); // 프론트엔드 호환성
    }
}
