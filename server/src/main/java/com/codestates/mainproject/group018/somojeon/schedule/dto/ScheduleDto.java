package com.codestates.mainproject.group018.somojeon.schedule.dto;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import com.codestates.mainproject.group018.somojeon.team.entity.Team;
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
        private String longitude;
        private String latitude;

        private List<Record> records;
        private List<Candidate> candidates;
        private List<Team> teams;

        public void addClubId(Long clubId) {
            this.clubId = clubId;
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
        private String longitude;
        private String latitude;

        private List<Record> records;
        private List<Candidate> candidates;
        private List<Team> teams;

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
        private Long scheduleId;
        private String date;
        private String time;
        private LocalDateTime createdAt;
        private String placeName;
        private String longitude;
        private String latitude;
        private List<TeamDto.Response> teams;
        private List<RecordDto.Response> records;
        private List<CandidateDto.Response> candidates;
    }
}
