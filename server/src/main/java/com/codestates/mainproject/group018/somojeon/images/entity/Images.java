package com.codestates.mainproject.group018.somojeon.images.entity;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;

    @Column(nullable = false)
    private String url;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @OneToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Images(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Images{" +
                "imageId=" + imageId +
                ", url='" + url + '\'' +
                '}';
    }
}
