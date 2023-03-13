package com.codestates.mainproject.group018.somojeon.record.entity;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RecordCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordCandidateId;

    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    private Record record;

    @ManyToOne
    @JoinColumn(name = "CANDIDATE_ID")
    private Candidate candidate;
}
