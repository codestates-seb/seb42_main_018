package com.codestates.mainproject.group018.somojeon.schedule.mapper;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.schedule.dto.ScheduleDto;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.entity.TeamRecord;
import com.codestates.mainproject.group018.somojeon.team.entity.UserTeam;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, UserService.class, ClubService.class})
public interface ScheduleMapper {
    default Schedule schedulePutDtoToSchedule(ScheduleDto.Put requestBody, UserService userService, ClubService clubService){

        // 스케쥴
        Schedule schedule = new Schedule();
        schedule.setDate(requestBody.getDate());
        schedule.setTime(requestBody.getTime());
        schedule.setPlaceName(requestBody.getPlaceName());
        schedule.setLongitude(requestBody.getLongitude());
        schedule.setLatitude(requestBody.getLatitude());
        Club club = clubService.findClub(requestBody.getClubId());
        schedule.setClub(club);

        try {
            //팀 유저 팀 생성
            // 팀넘버, 유저 리스트
            if(requestBody.getTeamList() != null){
                List<ScheduleDto.ScheduleTeamDto> scheduleTeamDtos =  requestBody.getTeamList();
                List<Team> teams =  scheduleTeamDtos.stream().map(
                        scheduleTeamDto -> {
                            Integer teamNumber = scheduleTeamDto.getTeamNumber();
                            List<Long> membersIds = scheduleTeamDto.getMembersIds();
                            // 팀 만들기
                            Team team = new Team();
                            team.setTeamNumber(teamNumber);

                            // 유저 팀 생성 및 설정
                            membersIds.forEach(memberId -> {
                                // 유저 불러오기
                                User user =  userService.findUser(memberId);
                                // 유저 팀 만들기
                                UserTeam userTeam = new UserTeam();
                                userTeam.setUser(user);
                                userTeam.setTeam(team);
                                team.setUserTeam(userTeam);
                                user.setUserTeam(userTeam);
                                team.setSchedule(schedule);
                            });

                            // team 리턴
                            return team;
                        }).collect(Collectors.toList()); // team List로 콜렉트
                schedule.setTeamList(teams);
            }
            // RecordDto to Record, TeamRecord
            if(requestBody.getRecords() != null) {
                List<RecordDto.SchedulePost> recordDtoPosts = requestBody.getRecords();
                List<Record> records = recordDtoPosts.stream().map(
                        recordDtoPost -> {
                            Record record = new Record();
                            record.setFirstTeamNumber(recordDtoPost.getFirstTeamNumber());
                            record.setFirstTeamScore(recordDtoPost.getFirstTeamScore());
                            record.setSecondTeamNumber(recordDtoPost.getSecondTeamNumber());
                            record.setSecondTeamScore(recordDtoPost.getSecondTeamScore());
                            record.setSchedule(schedule);

                            TeamRecord teamRecord1 = new TeamRecord();
                            teamRecord1.setRecord(record);
                            record.addTeamRecord(teamRecord1);
                            Team team1 = schedule.getTeamList().stream().filter(team -> team.getTeamNumber() == 1)
                                    .findFirst().orElse(null);
                            teamRecord1.setTeam(team1);
                            team1.addTeamRecord(teamRecord1);

                            TeamRecord teamRecord2 = new TeamRecord();
                            teamRecord2.setRecord(record);
                            record.addTeamRecord(teamRecord2);
                            Team team2 = schedule.getTeamList().stream().filter(team -> team.getTeamNumber() == 2)
                                    .findFirst().orElse(null);
                            teamRecord2.setTeam(team2);
                            team2.addTeamRecord(teamRecord2);

                            return record;
                        }).collect(Collectors.toList());
                schedule.setRecords(records);
            }
            // CandidateDto to candidate
            if(requestBody.getCandidates() != null) {
                List<CandidateDto.SchedulePost> candidateDtoPosts = requestBody.getCandidates();
                List<Candidate> candidates = candidateDtoPosts.stream().map(candidateDtoPost -> {
                    Candidate candidate = new Candidate();
                    candidate.setUser(userService.findUser(candidateDtoPost.getUserId()));
                    candidate.setAttendance(Candidate.Attendance.ATTEND);
                    candidate.setSchedule(schedule);
                    return candidate;
                }).collect(Collectors.toList());
                schedule.setCandidates(candidates);
            }
        } catch (Exception e) {
            if (e instanceof DataAccessException) {
                // 데이터 저장 예외 처리
                DataAccessException dataAccessException = (DataAccessException) e;
                String exceptionMessage = dataAccessException.getMessage();
                if (exceptionMessage.contains("club")) {
                    throw new BusinessLogicException(ExceptionCode.CLUB_SAVE_ERROR);
                } else if (exceptionMessage.contains("team")) {
                    throw new BusinessLogicException(ExceptionCode.TEAM_SAVE_ERROR);
                } else if (exceptionMessage.contains("candidate")) {
                    throw new BusinessLogicException(ExceptionCode.CANDIDATE_SAVE_ERROR);
                } else if (exceptionMessage.contains("record")) {
                    throw new BusinessLogicException(ExceptionCode.RECORD_SAVE_ERROR);
                }
            }
            throw new BusinessLogicException(ExceptionCode.GENERAL_ERROR);
        }
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
                    response.setFirstTeamNumber(record.getFirstTeamNumber());
                    response.setFirstTeamScore(record.getFirstTeamScore());
                    response.setSecondTeamNumber(record.getSecondTeamNumber());
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

                    List<Long> users = scheduleTeamDto.getMembersIds();
                    users.stream()
                            .map(userId -> new UserTeam(userRepository.findByUserId(userId), team));
                    return team;
                })
                .collect(Collectors.toList());
    }
}

