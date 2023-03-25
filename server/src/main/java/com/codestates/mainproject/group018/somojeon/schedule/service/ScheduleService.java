package com.codestates.mainproject.group018.somojeon.schedule.service;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.candidate.repository.CandidateRepository;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.repository.ClubRepository;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.record.repository.RecordRepository;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.schedule.repository.ScheduleRepository;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.entity.TeamRecord;
import com.codestates.mainproject.group018.somojeon.team.repository.TeamRecordRepository;
import com.codestates.mainproject.group018.somojeon.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
    private final ClubService clubService;

    public Schedule createSchedule(Schedule schedule, long clubId, List<Record> records,
                                   List<Team> teamList, List<Candidate> candidates) {
        Club club = clubService.findVerifiedClub(clubId);
        schedule.setClub(club);
        schedule.setTeams(teamList);
        schedule.setCandidates(candidates);
        schedule.setRecords(records);

        try {

            // club 정보 저장
            club.getScheduleList().add(schedule);
            clubRepository.save(club);

            // team 정보 저장 (teamRecord 로 team 과 record 연결)
            for (Team team : teamList) {
                team.setSchedule(schedule);
                teamRepository.save(team);
                for (Record record : records) {
                    TeamRecord teamRecord = new TeamRecord(record, team);
                    teamRecordRepository.save(teamRecord);
                }
            }

            // candidate 정보 저장
            for (Candidate candidate : candidates) {
                candidate.setSchedule(schedule);
                candidate.setAttendance(Candidate.Attendance.ATTEND);
                candidateRepository.save(candidate);
            }

            // record 정보 저장
            for (Record record : records) {
                record.setSchedule(schedule);
                recordRepository.save(record);
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

    public Schedule updateSchedule(Schedule schedule, List<Record> records,
                                   List<Team> teamList, List<Candidate> candidates) {
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

        findSchedule.setRecords(records);
        findSchedule.setCandidates(candidates);
        findSchedule.setTeams(teamList);

        return scheduleRepository.save(findSchedule);
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

}
