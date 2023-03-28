package com.codestates.mainproject.group018.somojeon.candidate.dto;

import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

public class CandidateDto {

    @Getter
    @NoArgsConstructor
    public static class Post {

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchedulePost {
        Long candidateId;
        Long userId;
        String nickName;
        Candidate.Attendance attendance;

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
        private Long userId;
        private String nickName;
        Candidate.Attendance attendance;

        public String getAttendance() {
            return attendance.getStatus();
        }
    }
}
