package com.codestates.mainproject.group018.somojeon.club.repository;

import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserClubRepository extends JpaRepository<UserClub, Long> {
    Optional<UserClub> findByUserIdAndClubId(Long userId, Long clubId);
}
