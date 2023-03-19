package com.codestates.mainproject.group018.somojeon.schedule.repository;

import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findAllByClubId(Pageable pageable, Long clubId);
}
