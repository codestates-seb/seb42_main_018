package com.codestates.mainproject.group018.somojeon.category.controller;

import com.codestates.mainproject.group018.somojeon.category.dto.CategoryDto;
import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.category.mapper.CategoryMapper;
import com.codestates.mainproject.group018.somojeon.category.service.CategoryService;
import com.codestates.mainproject.group018.somojeon.dto.CategoryResponseDtos;
import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    @GetMapping
    public ResponseEntity<?> getCategories() {
        List<Category> categories = categoryService.getAllCategoryNames();

        return new ResponseEntity<>(
                new CategoryResponseDtos<>(mapper.categoryToCategoryResponseDtos(categories)), HttpStatus.OK);
    }

//    @GetMapping("/search")
//    public List<String> searchCategories(@RequestParam String keyword) {
//        return categoryService.findByKeyword(keyword);
//    }
}
