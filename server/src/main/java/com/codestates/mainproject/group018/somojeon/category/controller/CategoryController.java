package com.codestates.mainproject.group018.somojeon.category.controller;

import com.codestates.mainproject.group018.somojeon.category.dto.CategoryDto;
import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.category.mapper.CategoryMapper;
import com.codestates.mainproject.group018.somojeon.category.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }
    // 포스트 요청은 안하기로함.
//    @PostMapping
//    public ResponseEntity postCategory(@Valid @RequestBody CategoryDto.Post requestBody) {
//        // TODO-DW: 회원검증
//
//        Category response = categoryService.createCategory(mapper.categoryPostDtoToCategory(requestBody));
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(mapper.categoryToCategoryResponseDto(response)), HttpStatus.CREATED);
//    }

    @GetMapping
    public List<String> getAllCategoryNames() {
        return categoryService.getAllCategoryNames();
    }

//    @GetMapping("/search")
//    public List<String> searchCategories(@RequestParam String keyword) {
//        return categoryService.findByKeyword(keyword);
//    }
}
