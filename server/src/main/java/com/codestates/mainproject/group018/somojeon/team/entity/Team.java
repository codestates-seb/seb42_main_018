package com.codestates.mainproject.group018.somojeon.team.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String winLose;
}
