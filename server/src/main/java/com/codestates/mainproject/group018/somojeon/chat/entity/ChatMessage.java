package com.codestates.mainproject.group018.somojeon.chat.entity;

import com.codestates.mainproject.group018.somojeon.chat.dto.ChatMessageSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    private Long userId;

    private Long chatRoomId;

    @Column
    private String nickName;

    @Column
    private String message;

    @Column(updatable = false)
    private LocalDateTime sendTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @Enumerated(value = EnumType.STRING)
    private MessageType messageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    @Builder
    public ChatMessage(ChatRoom chatRoom, String nickName, String message) {
        this.userId = chatRoom.getUserClub().getUser().getUserId();
        this.chatRoomId = chatRoom.getChatRoomId();
        this.nickName = nickName;
        this.message = message;
    }

    public enum MessageType {
        ENTER, TALK, LEAVE;
    }

    public ChatMessage isEnterType() {
        this.messageType = MessageType.ENTER;
        return this;
    }

    public ChatMessage isLeaveType() {
        this.messageType = MessageType.LEAVE;
        return this;
    }
}
