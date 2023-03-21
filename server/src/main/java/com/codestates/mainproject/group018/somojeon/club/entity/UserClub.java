package com.codestates.mainproject.group018.somojeon.club.entity;

import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.level.etity.Level;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
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

    private Integer playCount;

    private Integer winCount;

    private float winRate;

    @Enumerated(value = EnumType.STRING)
    private ClubRole clubRole;

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
