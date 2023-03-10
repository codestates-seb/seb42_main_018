package com.codestates.mainproject.group018.somojeon.category.repository;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.categoryId = ?1 ORDER BY c.categoryId")
    Page<Category> findAllByCategoryId(Pageable pageable, Long categoryId);

    Optional<Category> findByCategoryName(String categoryName);
}
