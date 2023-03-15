package com.codestates.mainproject.group018.somojeon.oauth.entity;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OAuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    @Column(nullable = false)
    String registration;

    @Column(unique = true, nullable = false)
    Long registrationId;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    User user;

    public OAuthUser(String registration, Long registrationId, User user) {
        this.registration = registration;
        this.registrationId = registrationId;
        this.user = user;
    }
}
