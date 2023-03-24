package com.codestates.mainproject.group018.somojeon.club.entity;

import com.codestates.mainproject.group018.somojeon.club.enums.ClubMemberStatus;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.club.enums.JoinStatus;
import com.codestates.mainproject.group018.somojeon.level.etity.Level;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserClub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userClubId;

    private boolean isPlayer;

    private int playCount;

    private int winCount;

    private int loseCount;

    private int drawCount;

    private float winRate;

    private String content;

    private int joinCount;

    @Enumerated(value = EnumType.STRING)
    JoinStatus joinStatus;

    @Enumerated(value = EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    ClubMemberStatus clubMemberStatus;

    @Enumerated(value = EnumType.STRING)
    ClubRole clubRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEVEL_ID")
    private Level level;

}
