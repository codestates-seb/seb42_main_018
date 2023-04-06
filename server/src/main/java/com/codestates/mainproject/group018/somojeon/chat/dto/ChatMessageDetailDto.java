package com.codestates.mainproject.group018.somojeon.chat.dto;

import com.codestates.mainproject.group018.somojeon.chat.entity.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDetailDto {

    private Long chatId;
    private Long chatRoomId;

    private String roomId;
    private String nickName;
    private String message;

    public static ChatMessageDetailDto toChatMessageDetailDto(ChatMessage chatMessage) {
        ChatMessageDetailDto chatMessageDetailDto = new ChatMessageDetailDto();

        chatMessageDetailDto.setChatId(chatMessage.getChatId());
        chatMessageDetailDto.setChatRoomId(chatMessage.getChatRoom().getChatRoomId());
        chatMessageDetailDto.setRoomId(chatMessage.getChatRoom().getRoomId());
        chatMessageDetailDto.setNickName(chatMessage.getNickName());
        chatMessageDetailDto.setMessage(chatMessage.getMessage());

        return chatMessageDetailDto;
    }
}
