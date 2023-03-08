package com.codestates.mainproject.group018.somojeon.group.repository;

import com.codestates.mainproject.group018.somojeon.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
