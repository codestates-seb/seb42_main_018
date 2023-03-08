package com.codestates.mainproject.group018.somojeon.tag.entity;

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

}
