package com.codestates.mainproject.group018.somojeon.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
