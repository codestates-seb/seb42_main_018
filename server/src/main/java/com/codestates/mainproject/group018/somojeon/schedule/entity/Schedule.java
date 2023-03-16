package com.codestates.mainproject.group018.somojeon.schedule.entity;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.geo.Point;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @CreatedDate
    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @CreatedDate
    @Column(name = "CREATE_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(nullable = false)
    private String placeName;

    @Column(nullable = false, columnDefinition = "GEOMETRY")
    private Point point;

    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @OneToMany(mappedBy = "schedule")
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    private List<Candidate> candidates = new ArrayList<>();
}
