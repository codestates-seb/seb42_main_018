package com.codestates.mainproject.group018.somojeon.chat.entity;

import com.codestates.mainproject.group018.somojeon.chat.dto.ChatRoomDto;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private String roomId;

    private String roomName;

    @CollectionTable(name = "chatRoom_users")
    @JoinColumn(name = "CHAT_ROOM_ID")
    @ElementCollection(fetch = FetchType.LAZY)
    List<String> users = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_CLUB_ID")
    private UserClub userClub;

    @Builder
    public ChatRoom(UserClub userClub, String roomId, String roomName) {
        this.userClub = userClub;
        this.roomId = roomId;
        this.roomName = roomName;
    }
}

