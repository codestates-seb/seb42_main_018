package com.codestates.mainproject.group018.somojeon.category.controller;

import com.codestates.mainproject.group018.somojeon.category.dto.CategoryDto;
import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.category.mapper.CategoryMapper;
import com.codestates.mainproject.group018.somojeon.category.service.CategoryService;
import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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

    @PostMapping
    public ResponseEntity postCategory(@Valid @RequestBody CategoryDto.Post requestBody) {
        //TODO: 회원검증

        Category response = categoryService.createCategory(mapper.categoryPostDtoToCategory(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.categoryResponseDtoToCategory(response)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getCategories(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @Positive Long categoryId) {

        Page<Category> categoryPage = categoryService.findCategories(page, size, categoryId);
        List<Category> content = categoryPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        mapper.categoryResponseDtosToCategory(content), categoryPage), HttpStatus.OK);
    }

}
