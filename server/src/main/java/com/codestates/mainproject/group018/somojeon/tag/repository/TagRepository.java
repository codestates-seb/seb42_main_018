package com.codestates.mainproject.group018.somojeon.tag.repository;

import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
