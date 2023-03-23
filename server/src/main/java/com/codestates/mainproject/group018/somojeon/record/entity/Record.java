package com.codestates.mainproject.group018.somojeon.record.entity;

import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.team.entity.TeamRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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

    @Column(nullable = false)
    private String firstTeam;

    @Column(nullable = false)
    private String secondTeam;

    @Column(nullable = false)
    private Integer firstTeamScore;

    @Column(nullable = false)
    private Integer secondTeamScore;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    @OneToMany(mappedBy = "record")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "record")
    private List<TeamRecord> teamRecords = new ArrayList<>();

    public void addTeamRecord(TeamRecord teamRecord) {
        this.teamRecords.add(teamRecord);
        if (teamRecord.getRecord() != this) {
            teamRecord.setRecord(this);
        }
    }

    public void setTeamRecord(TeamRecord teamRecord) {
        teamRecords.add(teamRecord);
    }

}
