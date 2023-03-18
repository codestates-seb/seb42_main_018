package com.codestates.mainproject.group018.somojeon.team.mapper;

import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamMapper {
    Team teamPostDtoToTeam(TeamDto.Post requestBody);
    Team teamPatchDtoToTeam(TeamDto.Patch requestBody);
    TeamDto.Response teamToTeamResponseDto(Team team);
    List<TeamDto.Response> teamsToTeamResponseDtos(List<Team> teams);
}
