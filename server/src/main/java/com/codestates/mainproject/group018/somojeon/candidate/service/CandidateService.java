package com.codestates.mainproject.group018.somojeon.candidate.service;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.candidate.repository.CandidateRepository;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final UserService userService;

    public CandidateService(CandidateRepository candidateRepository,
                            UserService userService) {
        this.candidateRepository = candidateRepository;
        this.userService = userService;
    }

    public Candidate createCandidate(Candidate candidate) {
        userService.findVerifiedUser(candidate.getUser().getUserId()); // 유저 확인

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

    public Page<Candidate> findCandidates(int page, int size) {
        return candidateRepository.findAllByAttendance(
                PageRequest.of(page, size, Sort.by("candidateId").descending()), Candidate.Attendance.ATTEND);
    }

    public void deleteCandidate(long candidateId) {
        Candidate candidate = findCandidate(candidateId);

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
