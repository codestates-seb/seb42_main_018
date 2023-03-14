package com.codestates.mainproject.group018.somojeon.club.entity;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubStatus;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubMemberStatus;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clubId;

    @Column(nullable = false)
    private String clubName;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    private boolean isPrivate;

    @Column(nullable = false)
    private int viewCount;

    // 멤버 수를 기록해야한다.
    private int memberCount;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    ClubStatus clubStatus = ClubStatus.CLUB_ACTIVE;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    ClubMemberStatus clubMemberStatus = ClubMemberStatus.MEMBER_ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Tag> tagList = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Joins> joinsList = new ArrayList<>();


    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Record> recordList = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<UserClub> userClubList = new ArrayList<>();

    @OneToOne(mappedBy = "club", cascade = CascadeType.ALL)
    private Images images;
}
