package com.codestates.mainproject.group018.somojeon.schedule.mapper;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.schedule.dto.ScheduleDto;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import com.codestates.mainproject.group018.somojeon.team.entity.UserTeam;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface ScheduleMapper {
    Schedule schedulePostDtoToSchedule(ScheduleDto.Post requestBody);
    Schedule schedulePatchDtoToSchedule(ScheduleDto.Patch requestBody);
//    ScheduleDto.Response scheduleToScheduleResponseDto(Schedule schedule);
    default ScheduleDto.Response scheduleToScheduleResponseDto(Schedule schedule, UserMapper userMapper) {
        if (schedule == null) {
            return null;
        }

        return ScheduleDto.Response
                .builder()
                .scheduleId(schedule.getScheduleId())
                .date(schedule.getDate())
                .createdAt(schedule.getCreatedAt())
                .placeName(schedule.getPlaceName())
                .longitude(schedule.getLongitude())
                .latitude(schedule.getLatitude())
                .teams(userTeamsToUserTeamResponseDtos(schedule.getUserTeams(), userMapper))
                .records(recordsToRecordResponseDtos(schedule.getRecords()))
                .candidates(candidatesToCandidateResponseDtos(schedule.getCandidates()))
                .build();
    }

    default List<TeamDto.Response> userTeamsToUserTeamResponseDtos(List<UserTeam> userTeams,
                                                                   UserMapper userMapper) {
        return userTeams.stream()
                .map(userTeam -> {
                    TeamDto.Response response = new TeamDto.Response();
                    response.setTeamId(userTeam.getTeam().getTeamId());

                    List<UserTeam> teamUsers = userTeam.getTeam().getUserTeams();
                    List<User> users = teamUsers.stream()
                            .map(teamUser -> teamUser.getUser())
                            .collect(Collectors.toList());

                    response.setUsers(userMapper.usersToUserResponses(users));

                    return response;
                })
                .collect(Collectors.toList());
    }

    default List<RecordDto.Response> recordsToRecordResponseDtos(List<Record> records) {
        return records.stream()
                .map(record -> {
                    RecordDto.Response response = new RecordDto.Response();
                    response.setScheduleId(record.getSchedule().getScheduleId());
                    response.setRecordId(record.getRecordId());
                    response.setCreatedAt(record.getCreatedAt());
                    response.setFirstTeam(record.getFirstTeam());
                    response.setFirstTeamScore(record.getFirstTeamScore());
                    response.setSecondTeam(record.getSecondTeam());
                    response.setSecondTeamScore(record.getSecondTeamScore());


                    return response;
                })
                .collect(Collectors.toList());
    }

    default List<CandidateDto.Response> candidatesToCandidateResponseDtos(List<Candidate> candidates) {
        return candidates.stream()
                .map(candidate -> {
                    CandidateDto.Response response = new CandidateDto.Response();
                    response.setCandidateId(candidate.getCandidateId());
                    response.setScheduleId(candidate.getSchedule().getScheduleId());
                    response.setUserId(candidate.getUser().getUserId());
                    response.setNickName(candidate.getUser().getNickName());
                    response.setAttendance(candidate.getAttendance());

                    return response;
                })
                .collect(Collectors.toList());
    }

    List<ScheduleDto.Response> schedulesToScheduleResponseDtos(List<Schedule> schedules);
}

