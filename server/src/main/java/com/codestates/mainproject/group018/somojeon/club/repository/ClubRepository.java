package com.codestates.mainproject.group018.somojeon.club.repository;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
}
