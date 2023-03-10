package com.codestates.mainproject.group018.somojeon.user.entity;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import com.codestates.mainproject.group018.somojeon.record.entity.UserRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String nickName;

    private char gender;
    private int age;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Column(nullable = false)
    String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Images images;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Joins> joinsList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserClub> userClubList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRecord> userRecordList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Candidate> candidateList = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    UserStatus userStatus = UserStatus.MEMBER_NEW;



    public enum UserStatus{

        MEMBER_NEW("USER_NEW"),
        USER_ACTIVE("USER_ACTIVE"),
        USER_SLEEP("USER_SLEEP"),
        USER_QUIT("USER_QUIT");

        @Getter
        String status;

        UserStatus(String status) {
            this.status = status;
        }
    }



}
