package com.codestates.mainproject.group018.somojeon.comment.entity;

import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    private Record record;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
