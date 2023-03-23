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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECORD_ID")
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public TeamRecord(Record record, Team team) {
        addRecord(record);
        addTeam(team);
    }

    public void addRecord(Record record) {
        this.record = record;
        record.setTeamRecord(this);
    }

    public void addTeam(Team team) {
        this.team = team;
        team.setTeamRecord(this);
    }

}
