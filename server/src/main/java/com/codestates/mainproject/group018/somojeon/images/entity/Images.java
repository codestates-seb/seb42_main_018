package com.codestates.mainproject.group018.somojeon.images.entity;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;

    @Column
    private String fileName;

    @Column
    private String url;

    @OneToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}

