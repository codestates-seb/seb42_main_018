package com.codestates.mainproject.group018.somojeon.tag.repository;

import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);
//
//    @Query("SELECT t FROM Tag t WHERE t.club.id = :clubId")
//    List<Tag> findByClubId(@Param("clubId") Long clubId);
}
