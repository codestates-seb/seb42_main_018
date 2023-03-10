package com.codestates.mainproject.group018.somojeon.candidate.repository;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Page<Candidate> findAllByAttendance(Pageable pageable, Candidate.Attendance attendance);
}
