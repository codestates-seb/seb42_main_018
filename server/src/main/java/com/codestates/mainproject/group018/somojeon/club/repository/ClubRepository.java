package com.codestates.mainproject.group018.somojeon.club.repository;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByClubName(String clubName);

//    @Query("SELECT c FROM Club c WHERE c.viewCount = ?1 ORDER BY c.viewCount DESC")
//    Page<Club> findAllByClub(Pageable pageable, Club club);

    @Query("SELECT c FROM Club c WHERE LOWER(c.clubName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Club> findByKeyword(Pageable pageable, String keyword);

//    @Query("SELECT c FROM Club c LEFT OUTER JOIN FETCH c.tags WHERE c.clubId = :clubId")
//    Club findClubWithTags(@Param("clubId") Long clubId);

//    @Query("SELECT c FROM Club c LEFT OUTER JOIN FETCH c.tags")
//    Page<Club> findAllClubsWithTags(Pageable pageable);

    List<Club> findAllByCategoryName(String categoryName);
}
