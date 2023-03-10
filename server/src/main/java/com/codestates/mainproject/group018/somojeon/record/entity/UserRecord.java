package com.codestates.mainproject.group018.somojeon.record.entity;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRecordId;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String winLose;

    @Column(nullable = false)
    private String teamName;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    private Record record;
}
