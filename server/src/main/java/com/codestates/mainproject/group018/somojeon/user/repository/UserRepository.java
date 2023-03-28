package com.codestates.mainproject.group018.somojeon.user.repository;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.candidateList cd WHERE cd.candidateId = :candidateId")
    User findByCandidate(@Param("candidateId") long candidateId);
    @Query("SELECT c FROM Candidate c WHERE c.user.userId = :userId")
    User findByUserId(Long userId);
}
