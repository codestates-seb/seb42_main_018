package com.codestates.mainproject.group018.somojeon.candidate.service;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.candidate.repository.CandidateRepository;
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

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidate(Candidate candidate) {
        Candidate findCandidate = findVerifiedCandidate(candidate.getCandidateId());

        Optional.ofNullable(candidate.getAttendance())
                .ifPresent(findCandidate::setAttendance);

        return candidateRepository.save(candidate);
    }

    public Candidate findCandidate(long candidateId) {
        return findVerifiedCandidate(candidateId);
    }

    public Page<Candidate> findCandidates(int page, int size) {
        return candidateRepository.findAll(PageRequest.of(page, size, Sort.by("candidateId").descending()));
    }

    public void deleteCandidate(long candidateId) {
        Candidate candidate = findCandidate(candidateId);

        candidateRepository.delete(candidate);
    }

    public Candidate findVerifiedCandidate(long candidateId) {
        Optional<Candidate> optionalCandidate =
                candidateRepository.findById(candidateId);
        Candidate findCandidate =
                optionalCandidate.orElseThrow(() -> new RuntimeException("후보가 없습니다."));

        return findCandidate;
    }
}
