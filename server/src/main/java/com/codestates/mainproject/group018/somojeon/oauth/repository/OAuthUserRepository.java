package com.codestates.mainproject.group018.somojeon.oauth.repository;

import com.codestates.mainproject.group018.somojeon.oauth.entity.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthUserRepository extends JpaRepository<OAuthUser, Long> {
    public Optional<OAuthUser> findByRegistrationAndRegistrationId(String registration, Long registrationId);
}
