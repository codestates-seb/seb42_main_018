package com.codestates.mainproject.group018.somojeon.team.entity;

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
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private String winLoseDraw;

    @OneToMany(mappedBy = "team")
    private List<TeamRecord> teamRecords = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<UserTeam> userTeams = new ArrayList<>();

    public void addTeamRecord(TeamRecord teamRecord) {
        this.teamRecords.add(teamRecord);
        if (teamRecord.getTeam() != this) {
            teamRecord.setTeam(this);
        }
    }

    public void setTeamRecord(TeamRecord teamRecord) {
        teamRecords.add(teamRecord);
    }
}
