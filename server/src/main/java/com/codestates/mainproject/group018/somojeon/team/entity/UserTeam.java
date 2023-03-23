package com.codestates.mainproject.group018.somojeon.team.entity;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTeamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public UserTeam(User user, Team team) {
        addUser(user);
        addTeam(team);
    }

    public void addUser(User user) {
        this.user = user;
        user.setUserTeam(this);
    }

    public void addTeam(Team team) {
        this.team = team;
        team.setUserTeam(this);
    }

}
