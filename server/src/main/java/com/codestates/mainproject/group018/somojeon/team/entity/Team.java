package com.codestates.mainproject.group018.somojeon.team.entity;

import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
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
    private Integer teamNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamRecord> teamRecords = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
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

    public void addUserTeam(UserTeam userTeam) {
        this.userTeams.add(userTeam);
        if (userTeam.getTeam() != this) {
            userTeam.setTeam(this);
        }
    }

    public void setUserTeam(UserTeam userTeam) {
        userTeams.add(userTeam);
    }
}
