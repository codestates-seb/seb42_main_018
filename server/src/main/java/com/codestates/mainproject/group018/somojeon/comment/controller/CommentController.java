package com.codestates.mainproject.group018.somojeon.comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {

    @PostMapping
    public ResponseEntity postComment() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchComment() {
        return null;
    }

    @GetMapping
    public ResponseEntity getComment() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity deleteComment() {
        return null;
    }
}
