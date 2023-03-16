package com.codestates.mainproject.group018.somojeon.club.repository;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByClubName(String clubName);

//    @Query("SELECT c FROM Club c WHERE c.viewCount = ?1 ORDER BY c.viewCount DESC")
//    Page<Club> findAllByClub(Pageable pageable, Club club);

    @Query("SELECT c FROM Club c WHERE c.isPrivate = false AND (LOWER(c.clubName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Club> findPublicClubsByKeyword(Pageable pageable, String keyword);

    @Query("SELECT c FROM Club c WHERE c.isPrivate = false")
    Page<Club> findAllPublicClubs(Pageable pageable);
}
