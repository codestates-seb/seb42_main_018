package com.codestates.mainproject.group018.somojeon.level.etity;

import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long levelId;

    private String levelName;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private List<UserClub> userClubList = new ArrayList<>();
}
