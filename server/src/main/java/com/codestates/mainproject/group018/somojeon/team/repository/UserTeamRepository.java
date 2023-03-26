package com.codestates.mainproject.group018.somojeon.team.repository;

import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.entity.UserTeam;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    @Query("SELECT ut FROM UserTeam ut WHERE ut.user = :user AND ut.team = :team")
    UserTeam findByUserAndTeam(@Param("user") User user, @Param("team")Team team);

    boolean existsByUserAndTeam(User user, Team team);
}
