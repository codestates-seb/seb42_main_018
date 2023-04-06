package com.codestates.mainproject.group018.somojeon.chat.controller;

import com.codestates.mainproject.group018.somojeon.chat.service.ChatService;
import com.codestates.mainproject.group018.somojeon.club.service.UserClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class ChatController {

    private UserClubService userClubService;
    private ChatService chatService;


    // 유저클럽 채팅방 목록 조회
    @GetMapping("clubs/{userClub-id}")
    public String myChatting(@PathVariable("userClub-id") Long userClubId,
                             Model model) {

        log.info("# All Chat Rooms");
        model.addAttribute("rooms", chatService.findChatRooms(userClubId));

        return "/clubs";

    }

    // 유저클럽 채팅방 개설
    @PostMapping("/rooms")
    public String createChat(@RequestParam String rooName, Long userClubId, RedirectAttributes redirectAttributes) {
        log.info("# Create Chat Room , name: " + rooName);
        redirectAttributes.addAttribute(chatService.create(rooName, userClubId));

        return "redirect:/clubs/" + userClubId;
    }

    // 유저클럽 채팅방 조회
    @GetMapping("/rooms")
    public void getRooms(String roomId, Model model) {
        log.info("# get Chat Room, roomId : " + roomId);
        model.addAttribute("room", chatService.findRoomById(roomId));
    }
}
