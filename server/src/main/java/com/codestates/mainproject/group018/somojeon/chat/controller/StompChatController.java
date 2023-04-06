package com.codestates.mainproject.group018.somojeon.chat.controller;

import com.codestates.mainproject.group018.somojeon.chat.dto.ChatMessageDetailDto;
import com.codestates.mainproject.group018.somojeon.chat.dto.ChatMessageSaveDto;
import com.codestates.mainproject.group018.somojeon.chat.entity.ChatMessage;
import com.codestates.mainproject.group018.somojeon.chat.entity.ChatRoom;
import com.codestates.mainproject.group018.somojeon.chat.repository.ChatMessageRepository;
import com.codestates.mainproject.group018.somojeon.chat.repository.ChatRoomRepository;
import com.codestates.mainproject.group018.somojeon.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;


    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageSaveDto message) {
        message.setMessage(message.getNickName() + "님이 채팅방에 입장하였습니다.");

        List<ChatMessage> chatList = chatService.findChatMessages(message.getRoomId());
        if (chatList != null) {
            for (ChatMessage chatMessage : chatList) {
                message.setNickName(chatMessage.getNickName());
                message.setMessage(chatMessage.getMessage());
            }
        }
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId());
        ChatMessageSaveDto chatMessageSaveDto = new ChatMessageSaveDto(message.getRoomId(), message.getNickName(), message.getMessage());
        chatMessageRepository.save(ChatMessage.toChatMessage(chatMessageSaveDto, chatRoom));

    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageSaveDto message) {
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId());
        ChatMessageSaveDto chatMessageSaveDto = new ChatMessageSaveDto(message.getRoomId(), message.getNickName(), message.getMessage());
        chatMessageRepository.save(ChatMessage.toChatMessage(chatMessageSaveDto, chatRoom));

    }
}
