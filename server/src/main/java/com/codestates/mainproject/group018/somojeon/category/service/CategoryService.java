package com.codestates.mainproject.group018.somojeon.category.service;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.category.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
// 카테고리는 수정, 삭제 불가

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 카테고리 생성
    public Category createCategory(Category category) {
        verifyExistsCategoryName(category.getCategoryName());
        return categoryRepository.save(category);
    }

    // 카테고리 전체 조회
    public Page<Category> findCategories(int page, int size, Long categoryId) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAllByCategoryId(pageable, categoryId);
    }


    private void verifyExistsCategoryName(String categoryName) {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
        if (category.isPresent()) {
            throw new RuntimeException();
        }
    }
}
