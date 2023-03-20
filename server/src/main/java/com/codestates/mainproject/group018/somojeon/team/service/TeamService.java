package com.codestates.mainproject.group018.somojeon.team.service;

import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Team team) {
        Team findTeam = findVerifiedTeam(team.getTeamId());

        return teamRepository.save(findTeam);
    }

    public Team findTeam(long teamId) {
        return findVerifiedTeam(teamId);
    }

    public Page<Team> findTeams(int page, int size) {
        return teamRepository.findAll(PageRequest.of(page, size, Sort.by("teamId").ascending()));
    }

    public void deleteteam(long teamId) {
        Team findTeam = findTeam(teamId);

        teamRepository.delete(findTeam);
    }

    public Team findVerifiedTeam(long teamId) {
        Optional<Team> optionalTeam =
                teamRepository.findById(teamId);
        Team findTeam = optionalTeam.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.TEAM_NOT_FOUND));

        return findTeam;
    }
}
