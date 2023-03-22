package com.codestates.mainproject.group018.somojeon.club.repository;

import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserClubRepository extends JpaRepository<UserClub, Long> {
//    @Query("SELECT uc FROM UserClub uc  WHERE uc.userId = :userId AND uc.clubId = :clubId")
    @Query("SELECT uc FROM UserClub uc  WHERE uc.user.userId = :userId AND uc.club = :clubId")
    Optional<UserClub> findByUserIdAndClubId(@Param("userId") Long userId, @Param("clubId") Long clubId);

    @Query("SELECT uc FROM UserClub uc  WHERE uc.user.userId = :userId")
    List<UserClub> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT uc FROM UserClub uc  WHERE uc.club.clubId = :clubId")
    Page<UserClub> findAllByClubId(Pageable pageable, @Param("clubId") Long clubId);
}
