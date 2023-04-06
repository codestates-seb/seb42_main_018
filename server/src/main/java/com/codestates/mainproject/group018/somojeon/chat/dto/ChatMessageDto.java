package com.codestates.mainproject.group018.somojeon.chat.dto;

import com.codestates.mainproject.group018.somojeon.chat.entity.ChatMessage;
import lombok.*;

public class ChatMessageDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private Long userId;
        private Long chatRoomId;
        private String nickName;
        private String message;

        public ChatMessageDto.Request toRequest(ChatMessage chatMessage) {
            return new Request(
                    chatMessage.getUserId(),
                    chatMessage.getChatRoomId(),
                    chatMessage.getNickName(),
                    chatMessage.getMessage());
        }

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long chatRoomId;
        private String message;
        private String nickName;
        private ChatMessage.MessageType messageType;

        public ChatMessageDto.Response toResponse(ChatMessage chatMessage) {
            return new Response(
                    chatMessage.getChatRoomId(),
                    chatMessage.getMessage(),
                    chatMessage.getNickName(),
                    ChatMessage.MessageType.TALK
            );
        }

    }
}
