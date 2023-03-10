package com.codestates.mainproject.group018.somojeon.images.repository;

import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
}
