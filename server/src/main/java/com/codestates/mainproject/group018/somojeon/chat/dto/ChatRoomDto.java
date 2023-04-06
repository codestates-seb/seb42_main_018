package com.codestates.mainproject.group018.somojeon.chat.dto;

import com.codestates.mainproject.group018.somojeon.chat.entity.ChatRoom;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ChatRoomDto {

    @Getter
    @AllArgsConstructor
    public static class Create {

        private String roomId;

        @NotNull(message = "채팅방 이름은 필수 입니다.")
        private String roomName;
            //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션
//    private Set<WebSocketSession> sessions = new HashSet<>();
    }

    @Data
    @AllArgsConstructor
    public static class Detail {

        private Long userClubId;

        private Long chatRoomId;
        private String roomId;
        private String roomName;

        public static ChatRoomDto.Detail toChatRoomDetail(ChatRoom chatRoom) {
            return new Detail(
                    chatRoom.getUserClub().getUserClubId(),
                    chatRoom.getChatRoomId(),
                    chatRoom.getRoomId(),
                    chatRoom.getRoomName()
            );
        }

    }

    @Data
    @AllArgsConstructor
    public static class Update {

        private Long chatRoomId;

        @NotNull(message = "채팅방 이름은 필수 입니다.")
        private String roomName;
    }


    public static class Lists {

        private List<Detail> chatRoomList;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long chatRoomId;
        private String roomId;
        private String roomName;
        private Integer userCount;
        private List<String> users;

        public static ChatRoomDto.Response toChatRoomResponse(ChatRoom chatRoom) {
            return new Response(
                    chatRoom.getChatRoomId(),
                    chatRoom.getRoomId(),
                    chatRoom.getRoomName(),
                    chatRoom.getUsers().size(),
                    chatRoom.getUsers()
            );
        }

    }


}
