package com.codestates.mainproject.group018.somojeon.club.repository;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.ClubTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubTagRepository extends JpaRepository<ClubTag, Long> {

    List<ClubTag> findAllByClub(Club club);
}
