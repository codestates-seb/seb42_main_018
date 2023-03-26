package com.codestates.mainproject.group018.somojeon.candidate.service;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.candidate.repository.CandidateRepository;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.repository.UserClubRepository;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
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
    private final UserClubRepository userClubRepository;
    private final ScheduleService scheduleService;

    public Candidate createCandidate(Candidate candidate) {

        UserClub userClub = new UserClub();
        if (userClub.isPlayer() == false) userClub.setPlayer(true);
        userClubRepository.save(userClub);

        candidate.setAttendance(Candidate.Attendance.ATTEND);

        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidate(Candidate candidate) {
        Candidate findCandidate = findVerifiedCandidate(candidate.getCandidateId());

        Optional.ofNullable(candidate.getAttendance())
                .ifPresent(findCandidate::setAttendance);

        return candidateRepository.save(findCandidate);
    }

    public Candidate findCandidate(long candidateId) {
        return findVerifiedCandidate(candidateId);
    }

    public Page<Candidate> findCandidates(long scheduleId, int page, int size) {
        scheduleService.findVerifiedSchedule(scheduleId);
        return candidateRepository.findBySchedule(scheduleId,
                PageRequest.of(page, size, Sort.by("candidateId")), Candidate.Attendance.ATTEND);
    }

    public void deleteCandidate(long candidateId) {
        Candidate candidate = findCandidate(candidateId);

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
