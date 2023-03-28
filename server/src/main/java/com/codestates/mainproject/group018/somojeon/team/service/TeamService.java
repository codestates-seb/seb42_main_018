package com.codestates.mainproject.group018.somojeon.team.service;

import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.schedule.service.ScheduleService;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final ScheduleService scheduleService;
    private final ClubService clubService;

    public Team createTeam(Team team, long clubId, long scheduleId) {
        scheduleService.findVerifiedSchedule(scheduleId);
        clubService.findVerifiedClub(clubId);

        return teamRepository.save(team);
    }

    public Team updateTeam(Team team, long clubId, long scheduleId) {
        Team findTeam = findVerifiedTeam(team.getTeamId());
        clubService.findClub(clubId);
        scheduleService.findSchedule(scheduleId);

        return teamRepository.save(findTeam);
    }

    public Team findTeam(long teamId, long clubId, long scheduleId) {
        clubService.findClub(clubId);
        scheduleService.findSchedule(scheduleId);
        return findVerifiedTeam(teamId);
    }

    public Page<Team> findTeams(long clubId, long scheduleId, int page, int size) {
        clubService.findClub(clubId);
        scheduleService.findSchedule(scheduleId);
        return teamRepository.findAll(PageRequest.of(page, size, Sort.by("teamId").ascending()));
    }

    public void deleteTeam(long teamId, long clubId, long scheduleId) {
        Team findTeam = findTeam(teamId, clubId, scheduleId);

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
