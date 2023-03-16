package com.codestates.mainproject.group018.somojeon.team.entity;

import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TeamRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamRecordId;

    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    private Record record;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
}
