package com.codestates.mainproject.group018.somojeon.candidate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @PostMapping
    public ResponseEntity postCandidate() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchCandidate() {
        return null;
    }

    @GetMapping
    public ResponseEntity getCandidate() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity deleteCandidate() {
        return null;
    }
}
