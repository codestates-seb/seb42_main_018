package com.codestates.mainproject.group018.somojeon.candidate.service;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.candidate.repository.CandidateRepository;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final ScheduleService scheduleService;
    private final ClubService clubService;

    public Candidate createCandidate(Candidate candidate, long clubId, long scheduleId) {
        clubService.findClub(clubId);
        scheduleService.findSchedule(scheduleId);
        UserClub userClub = new UserClub();
        if (userClub.isPlayer() == false) userClub.setPlayer(true);
        candidate.setAttendance(Candidate.Attendance.ATTEND);

        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidate(Candidate candidate, long clubId, long scheduleId) {
        Candidate findCandidate = findVerifiedCandidate(candidate.getCandidateId());
        clubService.findClub(clubId);
        scheduleService.findSchedule(scheduleId);

        Optional.ofNullable(candidate.getAttendance())
                .ifPresent(findCandidate::setAttendance);

        return candidateRepository.save(findCandidate);
    }

    public Candidate findCandidate(long candidateId, long clubId, long scheduleId) {
        clubService.findClub(clubId);
        scheduleService.findSchedule(scheduleId);
        return findVerifiedCandidate(candidateId);
    }

    public Page<Candidate> findCandidates(long clubId, long scheduleId, int page, int size) {
        clubService.findClub(clubId);
        scheduleService.findSchedule(scheduleId);
        return candidateRepository.findAllByAttendance(
                PageRequest.of(page, size, Sort.by("candidateId").descending()), Candidate.Attendance.ATTEND);
    }

    public void deleteCandidate(long candidateId, long clubId, long scheduleId) {
        Candidate candidate = findCandidate(candidateId, clubId, scheduleId);

        UserClub userClub = new UserClub();
        if (userClub.isPlayer() == true) userClub.setPlayer(false);
        candidate.setAttendance(Candidate.Attendance.HOLD);

        candidateRepository.delete(candidate);
    }

    public Candidate findVerifiedCandidate(long candidateId) {
        Optional<Candidate> optionalCandidate =
                candidateRepository.findById(candidateId);
        Candidate findCandidate =
                optionalCandidate.orElseThrow(
                        () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return findCandidate;
    }
}
