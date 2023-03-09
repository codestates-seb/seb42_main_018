package com.codestates.mainproject.group018.somojeon.schedule.entity;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ScheduleCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleCandidateId;

    @ManyToOne
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "CANDIDATE_ID")
    private Candidate candidate;
}
