package com.codestates.mainproject.group018.somojeon.candidate.repository;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
