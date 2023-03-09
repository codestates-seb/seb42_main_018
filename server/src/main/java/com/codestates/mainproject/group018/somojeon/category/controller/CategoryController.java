package com.codestates.mainproject.group018.somojeon.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity getCategories() {
        return null;
    }

}
