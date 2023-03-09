package com.codestates.mainproject.group018.somojeon.schedule.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @PostMapping
    public ResponseEntity postSchedule() {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchSchedule() {
        return null;
    }

    @GetMapping
    public ResponseEntity getSchedule() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity deleteSchedule() {
        return null;
    }
}
