package com.codestates.mainproject.group018.somojeon.join.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/join")
public class JoinController {

    @PostMapping
    public ResponseEntity postJoin() {
        return null;
    }

    @GetMapping
    public ResponseEntity getJoins() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity deleteJoin() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchDecision() {
        return null;
    }
}
