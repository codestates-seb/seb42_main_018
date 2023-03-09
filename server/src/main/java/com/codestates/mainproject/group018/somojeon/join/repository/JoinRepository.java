package com.codestates.mainproject.group018.somojeon.join.repository;

import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinRepository extends JpaRepository<Joins, Long> {
}
