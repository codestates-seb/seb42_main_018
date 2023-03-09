package com.codestates.mainproject.group018.somojeon.join.entity;

import com.codestates.mainproject.group018.somojeon.group.entity.Club;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Joins {

    @Id
    @GeneratedValue
    private Long joinsId;

    @NotNull
    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    //TODO: USER 매핑
}
