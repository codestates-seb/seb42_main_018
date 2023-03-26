package com.codestates.mainproject.group018.somojeon.club.entity;

import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClubTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public ClubTag(Club club, Tag tag) {
        addClub(club);
        addTag(tag);
    }

    public void addClub(Club club) {
        this.club = club;
        club.setClubTag(this);
    }

    public void addTag(Tag tag) {
        this.tag = tag;
        tag.setClubTag(this);
    }
}
