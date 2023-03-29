package com.codestates.mainproject.group018.somojeon.category.service;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
// 카테고리는 수정, 삭제 불가

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategoryNames() {
        return categoryRepository.findAll();
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
        } else {
            saveCategory(categoryName);
        }
    }
}
