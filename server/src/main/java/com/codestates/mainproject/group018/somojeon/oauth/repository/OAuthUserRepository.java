package com.codestates.mainproject.group018.somojeon.oauth.repository;

import com.codestates.mainproject.group018.somojeon.oauth.entity.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthUserRepository extends JpaRepository<OAuthUser, Long> {
    Optional<OAuthUser> findByRegistrationAndRegistrationId(String registration, Long registrationId);
    @Query("SELECT o FROM OAuthUser o JOIN FETCH o.user u WHERE o.registration = :registration AND o.registrationId = :registrationId")
    Optional<OAuthUser> findByRegistrationAndRegistrationIdWithUser(@Param("registration")String registration, @Param("registrationId")Long registrationId);

    @Query("SELECT o FROM OAuthUser o INNER JOIN o.user user WHERE user.email =:email")
    Optional<OAuthUser> findByUserEmail(@Param("email")String email);

    Optional<OAuthUser> findByRegistrationId(@Param("email")String email);
}
