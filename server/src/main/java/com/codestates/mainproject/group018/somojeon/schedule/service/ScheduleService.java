package com.codestates.mainproject.group018.somojeon.schedule.service;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.schedule.repository.ScheduleRepository;
import com.codestates.mainproject.group018.somojeon.team.entity.UserTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClubService clubService;

    public Schedule createSchedule(Schedule schedule, long clubId, List<Record> records,
                                   List<UserTeam> userTeams, List<Candidate> candidates) {
        schedule.setClub(clubService.findVerifiedClub(clubId));
        schedule.setUserTeams(userTeams);
        schedule.setCandidates(candidates);
        schedule.setRecords(records);

        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule, List<Record> records,
                                   List<UserTeam> userTeams, List<Candidate> candidates) {
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
        findSchedule.setUserTeams(userTeams);

        return scheduleRepository.save(findSchedule);
    }

    public Schedule findSchedule(long scheduleId) {
        return findVerifiedSchedule(scheduleId);
    }

    public Page<Schedule> findSchedules(long clubId, int page, int size) {
        clubService.findVerifiedClub(clubId);
        return scheduleRepository.findAll(PageRequest.of(page, size, Sort.by("scheduleId")));
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
