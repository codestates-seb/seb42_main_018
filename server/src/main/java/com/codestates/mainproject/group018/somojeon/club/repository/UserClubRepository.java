package com.codestates.mainproject.group018.somojeon.club.repository;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubMemberStatus;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserClubRepository extends JpaRepository<UserClub, Long> {
//    @Query("SELECT uc FROM UserClub uc  WHERE uc.userId = :userId AND uc.clubId = :clubId")
    @Query("SELECT uc FROM UserClub uc  WHERE uc.user.userId = :userId AND uc.club.clubId = :clubId")
//    @Query("SELECT uc FROM UserClub uc  WHERE uc.user.userId AND uc.club.clubId = ?1 ORDER BY")
    Optional<UserClub> findByUserIdAndClubId(@Param("userId") Long userId, @Param("clubId") Long clubId);

    @Query("SELECT uc FROM UserClub uc  WHERE uc.user.userId = :userId")
    List<UserClub> findAllByUserId(@Param("userId") Long userId);

//    @Query("SELECT uc FROM UserClub uc  WHERE uc.club.clubId = :clubId AND uc.clubRole = clubRole")
//    Page<UserClub> findAllByClubId(Pageable pageable, @Param("clubId") Long clubId, ClubRole clubRole);

    Page<UserClub> findAll(Pageable pageable);

    @Query("SELECT uc FROM UserClub uc WHERE uc.clubMemberStatus = :clubMemberStatus AND uc.club.clubId = :clubId")
    Page<UserClub> findByClubMemberStatus(Pageable pageable, Long clubId, ClubMemberStatus clubMemberStatus);

    @Query("SELECT uc FROM UserClub uc WHERE uc.user = :user AND uc.club = :club")
    UserClub findByUserAndClub(@Param("user") User user, @Param("club") Club club);

    boolean existsByUserAndClub(User user, Club club);
}
