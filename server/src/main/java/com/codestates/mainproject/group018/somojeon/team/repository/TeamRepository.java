package com.codestates.mainproject.group018.somojeon.team.repository;

import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
