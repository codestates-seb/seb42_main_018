package com.codestates.mainproject.group018.somojeon.candidate.entity;

import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    Attendance attendance = Attendance.HOLD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

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
