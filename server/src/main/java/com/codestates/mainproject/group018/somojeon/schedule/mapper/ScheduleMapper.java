package com.codestates.mainproject.group018.somojeon.schedule.mapper;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.schedule.dto.ScheduleDto;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.entity.UserTeam;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface ScheduleMapper {
    Schedule schedulePostDtoToSchedule(ScheduleDto.Post requestBody);
    Schedule schedulePutDtoToSchedule(ScheduleDto.Put requestBody);
    Schedule scheduleAttendPostDtoToSchedule(ScheduleDto.attendPost requestBody);
    Schedule scheduleAbsentPostDtoToSchedule(ScheduleDto.absentPost requestBody);

    //    ScheduleDto.Response scheduleToScheduleResponseDto(Schedule schedule);
    ScheduleDto.Response scheduleToScheduleResponseDto(Schedule schedule, UserMapper userMapper);

    List<TeamDto.Response> teamsToTeamResponseDtos(List<Team> teamList, UserMapper userMapper);

    List<RecordDto.Response> recordsToRecordResponseDtos(List<Record> records);

    List<CandidateDto.Response> candidatesToCandidateResponseDtos(List<Candidate> candidates);

    List<ScheduleDto.Response> schedulesToScheduleResponseDtos(List<Schedule> schedules, UserMapper userMapper);

    List<Team> ScheduleTeamToTeamListDtos(List<ScheduleDto.ScheduleTeamDto> scheduleTeamDtos);

}

