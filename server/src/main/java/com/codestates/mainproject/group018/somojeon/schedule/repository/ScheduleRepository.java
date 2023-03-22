package com.codestates.mainproject.group018.somojeon.schedule.repository;

import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
