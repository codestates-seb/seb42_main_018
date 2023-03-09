package com.codestates.mainproject.group018.somojeon.record.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/records")
public class RecordController {

    @PostMapping
    public ResponseEntity postRecord() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchRecord() {
        return null;
    }

    @GetMapping
    public ResponseEntity getRecord() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity deleteRecord() {
        return null;
    }
}
