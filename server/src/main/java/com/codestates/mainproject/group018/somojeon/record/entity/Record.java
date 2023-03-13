package com.codestates.mainproject.group018.somojeon.record.entity;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
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
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

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

    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    private Record record;

    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @OneToMany(mappedBy = "record")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "record")
    private List<UserRecord> userRecords = new ArrayList<>();

    @OneToMany(mappedBy = "record")
    private List<RecordCandidate> recordCandidates = new ArrayList<>();
}
