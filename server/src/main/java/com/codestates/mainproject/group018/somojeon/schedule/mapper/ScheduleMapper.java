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
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, UserService.class})
public interface ScheduleMapper {
    Schedule schedulePostDtoToSchedule(ScheduleDto.Post requestBody);
    default Schedule schedulePutDtoToSchedule(ScheduleDto.Put requestBody, UserService userService){
        List<ScheduleDto.ScheduleTeamDto> scheduleTeamDtos =  requestBody.getTeamList();
        // 스케쥴
        Schedule schedule = new Schedule();

        //팀 유저 팀 생성
        // 팀넘버, 유저 리스트
        List<Team> teams =  scheduleTeamDtos.stream().map(
                scheduleTeamDto -> {
                    Integer teamNumber = scheduleTeamDto.getTeamNumber();
                    List<Long> membersIds = scheduleTeamDto.getMembersId();
                    // 팀 만들기
                    Team team = new Team();
                    team.setTeamNumber(teamNumber);

                    membersIds.forEach(membersId -> {
                                // 유저 불러오기
                                User user =  userService.findUser(membersId);
                                // 유저 팀 만들기
                                UserTeam userTeam = new UserTeam();
                                userTeam.setUser(user);
                                userTeam.setTeam(team);
                                team.setUserTeam(userTeam);
                                user.setUserTeam(userTeam);});
                    // team 리턴
                    return team;
                }).collect(Collectors.toList()); // team List로 콜렉트
        schedule.setTeamList(teams);

        //
        return schedule;
    }
    Schedule scheduleAttendPostDtoToSchedule(ScheduleDto.attendPost requestBody);
    Schedule scheduleAbsentPostDtoToSchedule(ScheduleDto.absentPost requestBody);
//    ScheduleDto.Response scheduleToScheduleResponseDto(Schedule schedule);
    default ScheduleDto.Response scheduleToScheduleResponseDto(Schedule schedule, UserMapper userMapper) {
        if (schedule == null) {
            return null;
        }

        return ScheduleDto.Response
                .builder()
                .clubId(schedule.getClub().getClubId())
                .scheduleId(schedule.getScheduleId())
                .date(schedule.getDate())
                .time(schedule.getTime())
                .createdAt(schedule.getCreatedAt())
                .placeName(schedule.getPlaceName())
                .longitude(schedule.getLongitude())
                .latitude(schedule.getLatitude())
                .teamList(teamsToTeamResponseDtos(schedule.getTeamList(), userMapper))
                .records(recordsToRecordResponseDtos(schedule.getRecords()))
                .candidates(candidatesToCandidateResponseDtos(schedule.getCandidates()))
                .build();
    }

    default List<TeamDto.Response> teamsToTeamResponseDtos(List<Team> teamList,
                                                                   UserMapper userMapper) {
        if (teamList == null) {
            return null;
        }

        return teamList.stream()
                .map(team -> {
                    TeamDto.Response response = new TeamDto.Response();
                    response.setTeamId(team.getTeamId());
                    response.setTeamNumber(team.getTeamNumber());

                    List<UserTeam> userTeams = team.getUserTeams();
                    List<User> users = userTeams.stream()
                            .map(userTeam -> userTeam.getUser())
                            .collect(Collectors.toList());

                    response.setUsers(userMapper.usersToUserResponses(users));

                    return response;
                })
                .collect(Collectors.toList());
    }

    default List<RecordDto.Response> recordsToRecordResponseDtos(List<Record> records) {
        if (records == null) {
            return null;
        }

        return records.stream()
                .map(record -> {
                    RecordDto.Response response = new RecordDto.Response();
                    response.setRecordId(record.getRecordId());
//                    response.setCreatedAt(record.getCreatedAt());
                    response.setFirstTeam(record.getFirstTeam());
                    response.setFirstTeamScore(record.getFirstTeamScore());
                    response.setSecondTeam(record.getSecondTeam());
                    response.setSecondTeamScore(record.getSecondTeamScore());


                    return response;
                })
                .collect(Collectors.toList());
    }

    default List<CandidateDto.Response> candidatesToCandidateResponseDtos(List<Candidate> candidates) {
        if (candidates == null) {
            return null;
        }
        return candidates.stream()
                .map(candidate -> {
                    CandidateDto.Response response = new CandidateDto.Response();
                    response.setCandidateId(candidate.getCandidateId());
                    response.setUserId(candidate.getUser().getUserId());
                    response.setNickName(candidate.getUser().getNickName());
                    response.setAttendance(candidate.getAttendance());

                    return response;
                })
                .collect(Collectors.toList());
    }

    default List<ScheduleDto.Response> schedulesToScheduleResponseDtos(List<Schedule> schedules,
                                                                       UserMapper userMapper) {
        if ( schedules == null ) {
            return null;
        }

        List<ScheduleDto.Response> list = new ArrayList<>( schedules.size() );
        for ( Schedule schedule : schedules ) {
            list.add( scheduleToScheduleResponseDto( schedule , userMapper) );
        }

        return list;
    }

    default List<Team> scheduleTeamToTeamListDtos(List<ScheduleDto.ScheduleTeamDto> scheduleTeamDtos,
                                                  UserRepository userRepository) {
        if (scheduleTeamDtos == null) {
            return null;
        }

        return scheduleTeamDtos.stream()
                .map(scheduleTeamDto -> {
                    Team team = new Team();
                    team.setTeamNumber(scheduleTeamDto.getTeamNumber());

                    List<Long> membersId = scheduleTeamDto.getMembersId();
                    membersId.stream()
                            .map(memberId -> new UserTeam(userRepository.findByUserId(memberId), team));
                    return team;
                })
                .collect(Collectors.toList());
    }
}

