package com.codestates.mainproject.group018.somojeon.group.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class GroupController {

    @PostMapping
    public ResponseEntity postGroup() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchGroup() {
        return null;
    }

    @GetMapping
    public ResponseEntity getGroup() {
        return null;
    }

    @GetMapping
    public ResponseEntity getGroups() {
        return null;
    }

    @GetMapping
    public ResponseEntity getMyGroups() {
        return null;
    }

    @GetMapping
    public ResponseEntity searchGroup() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity deleteGroup() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchGroupRole() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchGroupLeader() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchGroupMemberStatus() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchMemberQuitGroup() {
        return null;
    }


}
