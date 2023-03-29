package com.codestates.mainproject.group018.somojeon;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.category.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

// 카테고리를 애플리케이션 실행시 DB에 저장하는 클래스
@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        List<String> categories = Arrays.asList(
                "배드민턴","탁구","축구","풋살","농구","배구","골프","볼링","테니스","당구");

        for (String categoryName : categories) {
            if (categoryRepository.findByCategoryName(categoryName).isEmpty()) {
                categoryRepository.save(new Category(categoryName));
            }
        }
    }
}
