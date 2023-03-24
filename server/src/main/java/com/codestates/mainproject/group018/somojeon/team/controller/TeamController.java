package com.codestates.mainproject.group018.somojeon.team.controller;

import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.mapper.TeamMapper;
import com.codestates.mainproject.group018.somojeon.team.service.TeamService;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping("/clubs/{club-id}/schedules/{schedule-id}/teams")
    public ResponseEntity postTeam(@PathVariable("club-id") @Positive long clubId,
                                   @PathVariable("schedule-id") @Positive long scheduleId,
                                   @Valid @RequestBody TeamDto.Post requestBody) {
        requestBody.addClubId(clubId);
        requestBody.addScheduleId(scheduleId);

        Team team = teamMapper.teamPostDtoToTeam(requestBody);

        Team createdTeam = teamService.createTeam(team, clubId, scheduleId);
        URI location = UriCreator.createUri("/teams", createdTeam.getTeamId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/clubs/{club-id}/schedules/{schedule-id}/teams/{team-id}")
    public ResponseEntity patchTeam(@PathVariable("club-id") @Positive long clubId,
                                    @PathVariable("schedule-id") @Positive long scheduleId,
                                    @PathVariable("team-id") @Positive long teamId,
                                    @Valid @RequestBody TeamDto.Patch requestBody) {
        requestBody.addClubId(clubId);
        requestBody.addScheduleId(scheduleId);
        requestBody.addTeamId(teamId);

        Team team = teamService.updateTeam(teamMapper.teamPatchDtoToTeam(requestBody), clubId, scheduleId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(teamMapper.teamToTeamResponseDto(team)), HttpStatus.OK);
    }

    @GetMapping("/clubs/{club-id}/schedules/{schedule-id}/teams/{team-id}")
    public ResponseEntity getTeam(@PathVariable("club-id") @Positive long clubId,
                                  @PathVariable("schedule-id") @Positive long scheduleId,
                                  @PathVariable("team-id") @Positive long teamId) {
        Team team = teamService.findTeam(teamId, clubId, scheduleId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(teamMapper.teamToTeamResponseDto(team)), HttpStatus.OK);
    }


    @GetMapping("/clubs/{club-id}/schedules/{schedule-id}/teams")
    public ResponseEntity getTeams(@PathVariable("club-id") @Positive long clubId,
                                   @PathVariable("schedule-id") @Positive long scheduleId,
                                   @RequestParam("page") int page,
                                   @RequestParam("size") int size) {
        Page<Team> pageTeams = teamService.findTeams(clubId, scheduleId, page - 1, size);
        List<Team> teams = pageTeams.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(teamMapper.teamsToTeamResponseDtos(teams), pageTeams), HttpStatus.OK);
    }

    @DeleteMapping("/clubs/{club-id}/schedules/{schedule-id}/teams/{team-id}")
    public ResponseEntity deleteTeam(@PathVariable("club-id") @Positive long clubId,
                                     @PathVariable("schedule-id") @Positive long scheduleId,
                                     @PathVariable("team-id") @Positive long teamId) {
        teamService.deleteTeam(teamId, clubId, scheduleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
