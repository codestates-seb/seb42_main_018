package com.codestates.mainproject.group018.somojeon.images.entity;

import com.codestates.mainproject.group018.somojeon.group.entity.Group;
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

    @Column(nullable = false)
    private String url;

    @OneToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    //TODO : USER 매핑
}
