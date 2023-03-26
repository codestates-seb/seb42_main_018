package com.codestates.mainproject.group018.somojeon.candidate.repository;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Page<Candidate> findAllByAttendance(Pageable pageable, Candidate.Attendance attendance);

    @Query("SELECT c FROM Candidate c WHERE c.user = :user AND c.schedule = :schedule")
    Candidate findByUserAndSchedule(@Param("user") User user, @Param("schedule") Schedule schedule);

    boolean existsByUserAndSchedule(User user, Schedule schedule);

    @Query("SELECT cd FROM Candidate cd WHERE cd.schedule.scheduleId = :scheduleId AND cd.attendance = :attendance")
    Page<Candidate> findBySchedule(@Param("scheduleId") long scheduleId, Pageable pageable, @Param("attendance") Candidate.Attendance attendance);
}
