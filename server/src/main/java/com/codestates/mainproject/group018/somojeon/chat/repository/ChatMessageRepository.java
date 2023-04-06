package com.codestates.mainproject.group018.somojeon.chat.repository;

import com.codestates.mainproject.group018.somojeon.chat.dto.ChatMessageDetailDto;
import com.codestates.mainproject.group018.somojeon.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.chatRoom.roomId = :roomId")
    List<ChatMessage> findChatMessageByChatRoomId(String roomId);
}
