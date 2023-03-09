package com.codestates.mainproject.group018.somojeon.group.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(name = "/clubs")
public class ClubController {

    @PostMapping
    public ResponseEntity postClub() {
        return null;
    }

    @PatchMapping("/{club-id}")
    public ResponseEntity patchClub() {
        return null;
    }

    @GetMapping("/{club-id}")
    public ResponseEntity getClub() {
        return null;
    }

    @GetMapping
    public ResponseEntity getClubs() {
        return null;
    }

    @GetMapping("/my")
    public ResponseEntity getMyClub() {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity searchClub() {
        return null;
    }

    @DeleteMapping("/{club-id}")
    public ResponseEntity deleteClub() {
        return null;
    }

    @PatchMapping("/{club-id}/club-status")
    public ResponseEntity patchClubStatus() {
        return null;
    }

    @PatchMapping("/manage/{user-id}")
    public ResponseEntity patchMange() {
        return null;
    }

    @PatchMapping("/manage/{user-id}/leader")
    public ResponseEntity patchClubLeader() {
        return null;
    }

    @PatchMapping("/{user-id}/member-status")
    public ResponseEntity patchClubMemberStatus() {
        return null;
    }

    @PatchMapping("/{user-id}/member-quit")
    public ResponseEntity patchMemberQuitClub() {
        return null;
    }


}
