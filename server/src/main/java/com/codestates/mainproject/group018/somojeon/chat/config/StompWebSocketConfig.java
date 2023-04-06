package com.codestates.mainproject.group018.somojeon.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 웹소켓 생성
                .setAllowedOrigins("http://localhost:8080", "https://somojeon.site", "https://somojeon.vercel.app", " https://dev.somojeon.store")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // /sub/* 로 오는 요청에 대해 해당 url을 구독한 클라이언트들에게 메세지 전송
        registry.enableSimpleBroker("/sub");
        // /pub/* 로 오는 요청에 대해 Controller 의 MessageMapping 되어 있는 메서드 실행
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
