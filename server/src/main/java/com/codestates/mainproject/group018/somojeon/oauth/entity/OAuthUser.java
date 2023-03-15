package com.codestates.mainproject.group018.somojeon.oauth.entity;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
}
