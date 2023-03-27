package com.codestates.mainproject.group018.somojeon.team.entity;

import com.codestates.mainproject.group018.somojeon.record.entity.Record;
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

    @Column(nullable = false)
    private Integer score;

    private String winLoseDraw;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

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

    public void addUserTeam(UserTeam userTeam) {
        this.userTeams.add(userTeam);
        if (userTeam.getTeam() != this) {
            userTeam.setTeam(this);
        }
    }

    public void setUserTeam(UserTeam userTeam) {
        userTeams.add(userTeam);
    }

    public void updateScoreAndResult(Record record) {
        if (record.getFirstTeam().equals(this.getTeamNumber())) {
            this.setScore(this.getScore() + record.getFirstTeamScore());
            if (record.getFirstTeamScore() > record.getSecondTeamScore()) {
                this.setWinLoseDraw("win");
            } else if (record.getFirstTeamScore() < record.getSecondTeamScore()) {
                this.setWinLoseDraw("lose");
            } else {
                this.setWinLoseDraw("draw");
            }
        } else if (record.getSecondTeam().equals(this.getTeamNumber())) {
            this.setScore(this.getScore() + record.getSecondTeamScore());
            if (record.getSecondTeamScore() > record.getFirstTeamScore()) {
                this.setWinLoseDraw("win");
            } else if (record.getSecondTeamScore() < record.getFirstTeamScore()) {
                this.setWinLoseDraw("lose");
            } else {
                this.setWinLoseDraw("draw");
            }
        }
    }
}
