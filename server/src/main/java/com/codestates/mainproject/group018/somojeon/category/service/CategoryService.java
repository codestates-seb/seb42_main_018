package com.codestates.mainproject.group018.somojeon.category.service;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.category.repository.CategoryRepository;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
// 카테고리는 수정, 삭제 불가

    List<String> basicCategoryName;

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllCategoryNames() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(Category::getCategoryName).collect(Collectors.toList());
    }

//    public List<String> findByKeyword(String keyword) {
//        List<Category> categories = categoryRepository.findByKeyword(keyword);
//       return categories.stream().map(Category::getCategoryName).collect(Collectors.toList());
//    }

    public void saveCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        categoryRepository.save(category);
    }

    public void verifyExistsCategoryName(String categoryName) {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
        if (category.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.CATEGORY_EXISTS);
        } else {
            saveCategory(categoryName);
        }
    }
}
