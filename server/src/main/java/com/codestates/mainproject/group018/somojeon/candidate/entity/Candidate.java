package com.codestates.mainproject.group018.somojeon.candidate.entity;

import com.codestates.mainproject.group018.somojeon.record.entity.RecordCandidate;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    Attendance attendance = Attendance.ATTEND;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "candidate")
    private List<RecordCandidate> recordCandidates = new ArrayList<>();

    public enum Attendance {
        ATTEND("참석"),
        ABSENCE("불참"),
        HOLD("보류");

        @Getter
        String status;

        Attendance(String status) {
            this.status = status;
        }
    }
}
