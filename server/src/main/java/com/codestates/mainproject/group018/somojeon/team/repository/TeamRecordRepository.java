package com.codestates.mainproject.group018.somojeon.team.repository;

import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.team.entity.TeamRecord;
import com.codestates.mainproject.group018.somojeon.team.entity.UserTeam;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRecordRepository extends JpaRepository<TeamRecord, Long> {
    @Query("SELECT tr FROM TeamRecord tr WHERE tr.team = :team AND tr.record = :record")
    TeamRecord findByTeamAndRecord(@Param("team") Team team, @Param("record")Record record);
}
