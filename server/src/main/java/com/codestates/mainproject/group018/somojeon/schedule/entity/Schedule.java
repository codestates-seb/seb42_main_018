package com.codestates.mainproject.group018.somojeon.schedule.entity;

import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

    @Column(nullable = false)
    private String place;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "schedule")
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleCandidate> scheduleCandidates = new ArrayList<>();
}
