package com.codestates.mainproject.group018.somojeon.schedule.repository;

import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
