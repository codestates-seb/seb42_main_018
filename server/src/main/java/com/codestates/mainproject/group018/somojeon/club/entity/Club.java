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

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    private String categoryName;

    @Column(nullable = false)
    private boolean isPrivate;

    @Column(nullable = false)
    private int viewCount;

    // 멤버 수를 기록해야한다.
    private int memberCount;

//    @ElementCollection
//    @CollectionTable(name="tags", joinColumns = @JoinColumn(name= "TAG_ID"))
//    private List<String> tags;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    ClubStatus clubStatus = ClubStatus.CLUB_ACTIVE;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    ClubMemberStatus clubMemberStatus = ClubMemberStatus.MEMBER_ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "club")
    private List<Tag> tagList = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<Joins> joinsList = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<Record> recordList = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<UserClub> userClubList = new ArrayList<>();

    @OneToOne(mappedBy = "club", cascade = CascadeType.ALL)
    private Images images;

    public void addTag(Tag tag) {
        tagList.add(tag);
        tag.setClub(this);
    }

    public void removeTag(Tag tag) {
        tagList.remove(tag);
        tag.setClub(null);
    }
}
