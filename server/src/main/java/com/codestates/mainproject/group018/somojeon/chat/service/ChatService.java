package com.codestates.mainproject.group018.somojeon.chat.service;

import com.codestates.mainproject.group018.somojeon.chat.dto.ChatMessageDetailDto;
import com.codestates.mainproject.group018.somojeon.chat.dto.ChatRoomDto;
import com.codestates.mainproject.group018.somojeon.chat.entity.ChatMessage;
import com.codestates.mainproject.group018.somojeon.chat.entity.ChatRoom;
import com.codestates.mainproject.group018.somojeon.chat.repository.ChatMessageRepository;
import com.codestates.mainproject.group018.somojeon.chat.repository.ChatRoomRepository;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.repository.UserClubRepository;
import com.codestates.mainproject.group018.somojeon.club.service.UserClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserClubRepository userClubRepository;

    private Map<String, ChatRoomDto> chatRoomDtoMap;

    @PostConstruct
    private void init() {
        chatRoomDtoMap = new LinkedHashMap<>();
    }


    // 유저클럽에 모든 채팅방 조회
    public List<ChatRoom> findChatRooms(Long userClubId) {
        List<ChatRoom> chatRoomList = chatRoomRepository.findChatRoomsByUserClubId(userClubId);
        // 최신순으로 변환
        Collections.reverse(chatRoomList);
        return chatRoomList;
    }

    // 채팅방에 메세지 전체 조회
    public List<ChatMessage> findChatMessages(String roomId) {
        return chatMessageRepository.findChatMessageByChatRoomId(roomId);
    }



//    public ChatRoomDto createChatRoomDto(String nickName) {
//        create(nickName);
//        chatRoomDtoMap.put(room.getRoomId(), room);
//
//        return room;
//    }
//
    public ChatRoom create(String roomName, Long userClubId) {
        userClubRepository.findByUserClubId(userClubId);
        ChatRoom room = new ChatRoom();

        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomName(roomName);

        return room;
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }
}
