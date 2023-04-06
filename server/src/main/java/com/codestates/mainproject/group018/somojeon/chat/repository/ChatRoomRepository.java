package com.codestates.mainproject.group018.somojeon.chat.repository;


import com.codestates.mainproject.group018.somojeon.chat.dto.ChatRoomDto;
import com.codestates.mainproject.group018.somojeon.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.userClub.userClubId = :userClubId ")
    List<ChatRoom> findChatRoomsByUserClubId(@Param("userClubId") Long userClubId);

    ChatRoom findByRoomId(String roomId);




}
