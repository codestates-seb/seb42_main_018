package com.codestates.mainproject.group018.somojeon.tag.entity;

import com.codestates.mainproject.group018.somojeon.group.entity.Club;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tagId;

    @NotNull
    @Column(nullable = false)
    private String tagName;

    @Column(nullable = false)
    private int tagCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID")
    private Club club;
}
