package com.codestates.mainproject.group018.somojeon.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @PostMapping
    public ResponseEntity postCategory() {
        return null;
    }

    @GetMapping
    public ResponseEntity getCategories(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return null;
    }

}
