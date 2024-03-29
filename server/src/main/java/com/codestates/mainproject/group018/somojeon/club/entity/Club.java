package com.codestates.mainproject.group018.somojeon.club.entity;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubPrivateStatus;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubStatus;

import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @Column(nullable = false)
    private String clubName;

    @Column(length = 1000, columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    private String categoryName;

    @ElementCollection
    private List<String> tagList;


    private String clubImageUrl;

    private int viewCount;

    private int memberCount;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @Enumerated(value = EnumType.STRING)
    ClubStatus clubStatus = ClubStatus.CLUB_ACTIVE;

    @Enumerated(value = EnumType.STRING)
    ClubPrivateStatus clubPrivateStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<UserClub> userClubList = new ArrayList<>();



    public UserClub addUserClub(UserClub userClub) {
        this.userClubList.add(userClub);
        if (userClub.getClub() != this) {
            userClub.setClub(this);
        }

        return userClub;
    }
}
