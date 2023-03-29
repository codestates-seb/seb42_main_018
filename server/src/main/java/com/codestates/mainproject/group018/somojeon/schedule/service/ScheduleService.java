package com.codestates.mainproject.group018.somojeon.schedule.service;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.candidate.repository.CandidateRepository;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.repository.UserClubRepository;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.schedule.repository.ScheduleRepository;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CandidateRepository candidateRepository;
    private final UserClubRepository userClubRepository;
    private final ClubService clubService;
    private final UserService userService;

    public Schedule createSchedule(Schedule schedule, Long clubId) {
        List<Record> records =  schedule.getRecords();
        schedule.getClub().getClubId();
        goUserClub(records, clubId, false);
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule, long clubId, Long scheduleId) {
        deleteSchedule(scheduleId, clubId);
        List<Record> records =  schedule.getRecords();
        goUserClub(records, clubId, false);
        return scheduleRepository.save(schedule);
    }

    public void goUserClub(List<Record> records, Long clubId, boolean undo){
        // 레코드 하나 하나에 다수의 팀 레코드
        for (Record record : records) {
            Integer score1 = record.getFirstTeamScore();
            Integer score2 = record.getSecondTeamScore();
            List<Team> teams = record.getTeamRecords().stream().map(teamRecords -> teamRecords.getTeam()).collect(Collectors.toList());
            Team team1 = teams.get(0);
            Team team2 = teams.get(1);
            List<User> team1Users = team1.getUserTeams().stream().map(userTeam -> userTeam.getUser()).collect(Collectors.toList());
            List<User> team2Users = team2.getUserTeams().stream().map(userTeam -> userTeam.getUser()).collect(Collectors.toList());
            if (undo) {
                calcScores(team1Users, team2Users, score1, score2, clubId, true);
            } else calcScores(team1Users, team2Users, score1, score2, clubId, false);
        }
    }

    public void calcScores(List<User> team1Users, List<User> team2Users, int score1, int score2, Long clubId, boolean undo){
        List<UserClub> team1UserClubs = new ArrayList<>();
        List<UserClub> team2UserClubs = new ArrayList<>();

        for (User team1user : team1Users){
            team1UserClubs.add(team1user.getUserClubList().stream().filter(userClub -> userClub.getClub().getClubId() == clubId).findFirst().orElse(null));
        }

        for (User team2user : team2Users){
            team2UserClubs.add(team2user.getUserClubList().stream().filter(userClub -> userClub.getClub().getClubId() == clubId).findFirst().orElse(null));
        }

        int point = undo? -1 : 1;

        if (score1 > score2){
            team1UserClubs.stream().forEach(team1UserClub -> team1UserClub.setWinCount(team1UserClub.getWinCount() + point));
            team2UserClubs.stream().forEach(team2UserClub -> team2UserClub.setLoseCount(team2UserClub.getLoseCount() + point));

        } else if (score1 == score2) {
            team1UserClubs.stream().forEach(team1UserClub -> team1UserClub.setDrawCount(team1UserClub.getDrawCount() + point));
            team2UserClubs.stream().forEach(team2UserClub -> team2UserClub.setDrawCount(team2UserClub.getDrawCount() + point));

        } else{
            team1UserClubs.stream().forEach(team1UserClub -> team1UserClub.setLoseCount(team1UserClub.getLoseCount() + point));
            team2UserClubs.stream().forEach(team2UserClub -> team2UserClub.setWinCount(team2UserClub.getWinCount() + point));
        }
        Stream<UserClub> teamsUserClubs = Stream.concat(team1UserClubs.stream(), team2UserClubs.stream());
        teamsUserClubs.forEach(userClub -> {
            userClub.setPlayCount(userClub.getPlayCount() + point);
            userClub.setWinRate((float) userClub.getWinCount() / userClub.getPlayCount());
            userClubRepository.save(userClub);
        });
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
        if(findSchedule.getRecords() != null) goUserClub(findSchedule.getRecords(), clubId, true);

        scheduleRepository.delete(findSchedule);
    }

    public Schedule findVerifiedSchedule(long scheduleId) {
        Optional<Schedule> optionalSchedule =
                scheduleRepository.findById(scheduleId);
        Schedule findSchedule = optionalSchedule.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND));

        return findSchedule;
    }
}
