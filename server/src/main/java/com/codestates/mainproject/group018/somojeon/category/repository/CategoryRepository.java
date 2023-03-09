package com.codestates.mainproject.group018.somojeon.category.repository;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
