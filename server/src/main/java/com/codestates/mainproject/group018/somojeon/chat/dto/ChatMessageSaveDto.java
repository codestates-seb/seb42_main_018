package com.codestates.mainproject.group018.somojeon.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageSaveDto {

    private String roomId;
    private String nickName;
    private String message;
}
