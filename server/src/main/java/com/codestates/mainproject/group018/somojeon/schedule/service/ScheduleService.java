package com.codestates.mainproject.group018.somojeon.schedule.service;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.candidate.repository.CandidateRepository;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.repository.ClubRepository;
import com.codestates.mainproject.group018.somojeon.club.repository.UserClubRepository;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.record.repository.RecordRepository;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.schedule.repository.ScheduleRepository;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.entity.TeamRecord;
import com.codestates.mainproject.group018.somojeon.team.entity.UserTeam;
import com.codestates.mainproject.group018.somojeon.team.repository.TeamRecordRepository;
import com.codestates.mainproject.group018.somojeon.team.repository.TeamRepository;
import com.codestates.mainproject.group018.somojeon.team.repository.UserTeamRepository;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClubRepository clubRepository;
    private final RecordRepository recordRepository;
    private final TeamRepository teamRepository;
    private final TeamRecordRepository teamRecordRepository;
    private final CandidateRepository candidateRepository;
    private final UserClubRepository userClubRepository;
    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    private final ClubService clubService;
    private final UserService userService;

    public Schedule createSchedule(Schedule schedule, long clubId, List<Record> records,
                                   List<Team> teamList, List<Candidate> candidates) {
        Club club = clubService.findVerifiedClub(clubId);
        schedule.setClub(club);
        schedule.setCandidates(candidates);
        schedule.setTeamList(teamList);
        schedule.setRecords(records);

        try {

            // club 정보 저장
            club.getScheduleList().add(schedule);
            clubRepository.save(club);

            // candidate 정보 저장
            for (Candidate candidate : candidates) {
                candidate.setSchedule(schedule);
                candidate.setAttendance(Candidate.Attendance.ATTEND);
                candidateRepository.save(candidate);
            }

            // team 정보 저장
            for (Team team : teamList) {
                team.setSchedule(schedule);
                teamRepository.save(team);
            }

            // record 정보 저장
            for (Record record : records) {
                record.setSchedule(schedule);
                recordRepository.save(record);
                for (Team team : teamList) {
                    TeamRecord teamRecord = new TeamRecord(record, team);
                    teamRecordRepository.save(teamRecord);
                    record.addTeamRecord(teamRecord);
                }
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

    public Schedule updateSchedule(Schedule schedule, long clubId, List<Record> records,
                                   List<Team> teamList, List<Candidate> candidates) {
        Club club = clubService.findVerifiedClub(clubId);
        Schedule findSchedule = findVerifiedSchedule(schedule.getScheduleId());

        Optional.ofNullable(schedule.getDate())
                .ifPresent(findSchedule::setDate);
        Optional.ofNullable(schedule.getTime())
                .ifPresent(findSchedule::setTime);
        Optional.ofNullable(schedule.getPlaceName())
                .ifPresent(findSchedule::setPlaceName);
        Optional.ofNullable(schedule.getLongitude())
                .ifPresent(findSchedule::setLongitude);
        Optional.ofNullable(schedule.getLatitude())
                .ifPresent(findSchedule::setLatitude);

        findSchedule.setCandidates(candidates);
        findSchedule.setTeamList(teamList);
        findSchedule.setRecords(records);


        // candidate 정보 저장
        for (Candidate candidate : candidates) {
            candidate.setSchedule(findSchedule);
            User candidateUser = userRepository.findByCandidate(candidate.getCandidateId());
            UserClub userClub = userClubRepository.findByUserAndClub(candidateUser, club);
            candidate.setUser(userClub.getUser());
            candidate.setAttendance(Candidate.Attendance.ATTEND);
            candidateRepository.save(candidate);


            // team 정보 저장
            for (Team team : teamList) {
                team.setSchedule(findSchedule);

                // team에 속한 유저 중에서, candidate에서 가져온 유저와 일치하는 유저를 찾음
                User teamUser = null;
                for (UserTeam userTeam : team.getUserTeams()) {
                    User user = userTeam.getUser();
                    if (user.equals(candidateUser)) {
                        teamUser = user;
                        break;
                    }
                }

                if (teamUser != null) {
                    // 일치하는 유저가 있으면, candidate 정보와 team 정보 연결
                    UserClub teamUserClub = userClubRepository.findByUserAndClub(teamUser, club);
                    UserTeam userTeam = userTeamRepository.findByUserAndTeam(teamUserClub.getUser(), team);
                    candidate.setUser(teamUserClub.getUser());
                    team.addUserTeam(userTeam);

                    // team과 record 연결 및 정보 저장
                    for (Record record : records) {
                        if (team.getTeamRecords().equals(record)) {
                            TeamRecord teamRecord = teamRecordRepository.findByTeamAndRecord(team, record);
                            if (teamRecord == null) {
                                teamRecord = new TeamRecord();
                            }
                            teamRecord.setTeam(team);
                            teamRecord.setRecord(record);
                            teamRecordRepository.save(teamRecord);
                        }
                        record.setSchedule(findSchedule);
                        recordRepository.save(record);
                    }
                }
                teamRepository.save(team);
            }
        }

//        // record 정보 저장
//        for (Record record : records) {
//            record.setSchedule(findSchedule);
//            recordRepository.save(record);
//
//            // record와 team 연결 정보 저장
//            for (Team team : teamList) {
//                if (record.getTeamRecords().equals(team)) {
//                    TeamRecord teamRecord = teamRecordRepository.findByTeamAndRecord(team, record);
//                    if (teamRecord == null) {
//                        teamRecord = new TeamRecord();
//                    }
//                    teamRecord.setTeam(team);
//                    teamRecord.setRecord(record);
//                    teamRecordRepository.save(teamRecord);
//                }
//            }
//        }

        clubRepository.save(club);

        return findSchedule;
    }


    public Schedule attendCandidate(Schedule schedule, Long clubId, Long userId) {
        Schedule verifiedSchedule = findVerifiedSchedule(schedule.getScheduleId());
        Club club = clubService.findVerifiedClub(clubId);
        User user = userService.findVerifiedUser(userId);

        verifiedSchedule.setClub(club);

        UserClub userClub = userClubRepository.findByUserAndClub(user, club);
        if (userClub == null) {
            userClub = new UserClub();
            userClub.setUser(user);
            userClub.setClub(club);
        }
        userClub.setPlayer(true);
        userClubRepository.save(userClub);

        Candidate candidate = candidateRepository.findByUserAndSchedule(user, verifiedSchedule);
        if (candidate == null) {
            candidate = new Candidate();
            candidate.setUser(user);
            candidate.setSchedule(verifiedSchedule);
        }
        candidate.setAttendance(Candidate.Attendance.ATTEND);
        candidateRepository.save(candidate);


        return scheduleRepository.save(verifiedSchedule);
    }

    public Schedule absentCandidate(Schedule schedule, Long clubId, Long userId) {
        Schedule verifiedSchedule = findVerifiedSchedule(schedule.getScheduleId());
        Club club = clubService.findVerifiedClub(clubId);
        User user = userService.findVerifiedUser(userId);

        verifiedSchedule.setClub(club);

        UserClub userClub = userClubRepository.findByUserAndClub(user, club);
        userClub.setPlayer(false);
        userClubRepository.save(userClub);


        Candidate candidate = candidateRepository.findByUserAndSchedule(user, verifiedSchedule);
        candidate.setAttendance(Candidate.Attendance.ABSENT);
        candidateRepository.save(candidate);


        return scheduleRepository.save(verifiedSchedule);
    }

    public Schedule findSchedule(long scheduleId) {
        return findVerifiedSchedule(scheduleId);
    }

    public Page<Schedule> findSchedules(long clubId, int page, int size) {
        Club club = clubService.findVerifiedClub(clubId);
        return scheduleRepository.findScheduleByClub(club, PageRequest.of(page, size, Sort.by("scheduleId")));
    }

    public Schedule findScheduleByClub(long clubId, long scheduleId) {
        clubService.findVerifiedClub(clubId);
        Schedule findSchedule = findVerifiedSchedule(scheduleId);

        List<Candidate> candidates = findSchedule.getCandidates()
                .stream()
                .filter(c -> !c.getAttendance().equals(Candidate.Attendance.ABSENT))
                .collect(Collectors.toList());
        findSchedule.setCandidates(candidates);

        return findSchedule;
    }

    public void deleteSchedule(long scheduleId, long clubId) {
        clubService.findVerifiedClub(clubId);
        Schedule findSchedule = findSchedule(scheduleId);

        scheduleRepository.delete(findSchedule);
    }

    public Schedule findVerifiedSchedule(long scheduleId) {
        Optional<Schedule> optionalSchedule =
                scheduleRepository.findById(scheduleId);
        Schedule findSchedule = optionalSchedule.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND));

        return findSchedule;
    }

    public void calculateWinRate(UserClub userClub, Team team) {
        String winLoseDraw = team.getWinLoseDraw();
        int winCount = userClub.getWinCount();
        int drawCount = userClub.getDrawCount();
        int loseCount = userClub.getLoseCount();
        int playCount = userClub.getPlayCount();

        switch (winLoseDraw) {
            case "win":
                winCount++;
                break;
            case "lose":
                loseCount++;
                break;
            case "draw":
                drawCount++;
                break;
        }
        playCount++;

        double winRate = 0.0;
        if (playCount > 0) {
            winRate = ((double) (winCount * 3 + drawCount)) / (playCount * 3) * 100;
        }

        userClub.setWinCount(winCount);
        userClub.setDrawCount(drawCount);
        userClub.setLoseCount(loseCount);
        userClub.setPlayCount(playCount);
        userClub.setWinRate((float) winRate);

        userClubRepository.save(userClub);
    }
}
