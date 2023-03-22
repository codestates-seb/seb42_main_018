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
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping
    public ResponseEntity postTeam(@Valid @RequestBody TeamDto.Post requestBody) {
        Team team = teamMapper.teamPostDtoToTeam(requestBody);

        Team createdTeam = teamService.createTeam(team);
        URI location = UriCreator.createUri("/teams", createdTeam.getTeamId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{team-id}")
    public ResponseEntity patchTeam(@PathVariable("team-id") @Positive long teamId,
                                    @Valid @RequestBody TeamDto.Patch requestBody) {
        requestBody.addTeamId(teamId);

        Team team = teamService.updateTeam(teamMapper.teamPatchDtoToTeam(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(teamMapper.teamToTeamResponseDto(team)), HttpStatus.OK);
    }

    @GetMapping("/{team-id}")
    public ResponseEntity getTeam(@PathVariable("team-id") @Positive long teamId) {
        Team team = teamService.findTeam(teamId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(teamMapper.teamToTeamResponseDto(team)), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity getTeams(@RequestParam("page") int page,
                                   @RequestParam("size") int size) {
        Page<Team> pageTeams = teamService.findTeams(page - 1, size);
        List<Team> teams = pageTeams.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(teamMapper.teamsToTeamResponseDtos(teams), pageTeams), HttpStatus.OK);
    }

    @DeleteMapping("/{team-id}")
    public ResponseEntity deleteTeam(@PathVariable("team-id") @Positive long teamId) {
        teamService.deleteTeam(teamId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
