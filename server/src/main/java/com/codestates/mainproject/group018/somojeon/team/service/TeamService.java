package com.codestates.mainproject.group018.somojeon.team.service;

import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.record.service.RecordService;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.repository.TeamRepository;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
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
    private final UserService userService;
    private final RecordService recordService;

    public Team createTeam(Team team) {
//        User user = new User();
//        Record record = new Record();
//        userService.findVerifiedUser(user.getUserId());
//        recordService.findVerifiedRecord(record.getRecordId());

        return teamRepository.save(team);
    }

    public Team updateTeam(Team team) {
        Team findTeam = findVerifiedTeam(team.getTeamId());

        Optional.ofNullable(team.getScore())
                .ifPresent(findTeam::setScore);
        Optional.ofNullable(team.getWinLoseDraw())
                .ifPresent(findTeam::setWinLoseDraw);

        return teamRepository.save(findTeam);
    }

    public Team findTeam(long teamId) {
        return findVerifiedTeam(teamId);
    }

    public Page<Team> findTeams(int page, int size) {
        return teamRepository.findAll(PageRequest.of(page, size, Sort.by("teamId").ascending()));
    }

    public void deleteTeam(long teamId) {
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
