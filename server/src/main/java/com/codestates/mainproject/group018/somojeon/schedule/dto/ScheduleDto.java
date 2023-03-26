package com.codestates.mainproject.group018.somojeon.schedule.dto;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {

        private String date;
        private String time;

        private Long clubId;

        private String placeName;
        private Double longitude;
        private Double latitude;

        private List<Record> records;
        private List<Candidate> candidates;
        private List<Team> teamList;

        public void addClubId(Long clubId) {
            this.clubId = clubId;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class attendPost {
        private Long clubId;
        private Long scheduleId;
        private Long userId;

        public void addUserId(Long userId) {
            this.userId = userId;
        }

        public void addClubId(Long clubId) {
            this.clubId = clubId;
        }

        public void addScheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class absentPost {
        private Long clubId;
        private Long scheduleId;
        private Long userId;

        public void addUserId(Long userId) {
            this.userId = userId;
        }

        public void addClubId(Long clubId) {
            this.clubId = clubId;
        }

        public void addScheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private Long scheduleId;

        private Long clubId;

        private String date;
        private String time;

        private String placeName;
        private Double longitude;
        private Double latitude;

        private List<Record> records;
        private List<Candidate> candidates;
        private List<Team> teamList;

        public void addScheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
        }

        public void addClubId(Long clubId) {
            this.clubId = clubId;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long clubId;
        private Long scheduleId;
        private String date;
        private String time;
        private LocalDateTime createdAt;
        private String placeName;
        private Double longitude;
        private Double latitude;
        private List<TeamDto.Response> teamList;
        private List<RecordDto.Response> records;
        private List<CandidateDto.Response> candidates;
    }
}
