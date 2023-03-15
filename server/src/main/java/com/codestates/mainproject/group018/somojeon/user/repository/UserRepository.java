package com.codestates.mainproject.group018.somojeon.user.repository;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
