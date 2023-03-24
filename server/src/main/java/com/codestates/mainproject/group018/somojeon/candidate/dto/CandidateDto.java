package com.codestates.mainproject.group018.somojeon.candidate.dto;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CandidateDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private Long clubId;
        private Long scheduleId;

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
        private Long clubId;
        private Long scheduleId;
        private Long candidateId;

        Candidate.Attendance attendance;

        public void addCandidateId(Long candidateId) {
            this.candidateId = candidateId;
        }

        public void addClubId(Long clubId) {
            this.clubId = clubId;
        }

        public void addScheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long candidateId;
        private String nickName;
        Candidate.Attendance attendance;

        public String getAttendance() {
            return attendance.getStatus();
        }
    }
}
