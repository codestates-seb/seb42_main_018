package com.codestates.mainproject.group018.somojeon.candidate.dto;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CandidateDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private String nickName;

        Candidate.Attendance attendance;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private Long candidateId;

        private String nickName;

        Candidate.Attendance attendance;

        public void addCandidateId(Long candidateId) {
            this.candidateId = candidateId;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long candidateId;
        private Long userId;
        private String nickName;
        Candidate.Attendance attendance;

        public String getAttendance() {
            return attendance.getStatus();
        }
    }
}
