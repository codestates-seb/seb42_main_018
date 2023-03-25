package com.codestates.mainproject.group018.somojeon.candidate.dto;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import lombok.*;

public class CandidateDto {

    @Getter
    @NoArgsConstructor
    public static class Post {

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private Long candidateId;

        Candidate.Attendance attendance;

        public void addCandidateId(Long candidateId) {
            this.candidateId = candidateId;
        }

    }

    @Getter
    @Setter
    @Builder
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
