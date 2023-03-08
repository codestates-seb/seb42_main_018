package com.codestates.mainproject.group018.somojeon.group.entity;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.group.enums.GroupMemberStatus;
import com.codestates.mainproject.group018.somojeon.group.enums.GroupStatus;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.join.entity.Join;
import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
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
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;

    @NotNull
    @Column(nullable = false)
    private String groupName;

    @NotNull
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(nullable = false)
    private String local;

    @NotNull
    @Column(nullable = false)
    private boolean isPrivate;

    @Column(nullable = false)
    private int viewCount;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    GroupStatus groupStatus = GroupStatus.GROUP_ACTIVE;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    GroupMemberStatus groupMemberStatus = GroupMemberStatus.MEMBER_ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "join", cascade = CascadeType.ALL)
    private List<Join> joins = new ArrayList<>();

    //TODO: record OneToMany 매핑

    //TODO: userGroup OneToMany 매핑

    @OneToOne(mappedBy = "images", cascade = CascadeType.ALL)
    private Images images;
}
