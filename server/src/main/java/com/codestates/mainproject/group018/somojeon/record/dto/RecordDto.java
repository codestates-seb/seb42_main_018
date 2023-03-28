package com.codestates.mainproject.group018.somojeon.record.dto;

import com.codestates.mainproject.group018.somojeon.comment.dto.CommentDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class RecordDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private Long clubId;
        private Long scheduleId;
        private Integer firstTeam;
        private Integer secondTeam;
        private Integer firstTeamScore;
        private Integer secondTeamScore;

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
    public static class  SchedulePost {
        private Integer firstTeamScore;
        private Integer secondTeamScore;
        private Integer firstTeamNumber;
        private Integer secondTeamNumber;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long clubId;
        private Long scheduleId;
        private Long recordId;
        private Integer firstTeam;
        private Integer secondTeam;
        private Integer firstTeamScore;
        private Integer secondTeamScore;

        public void addRecordId(Long recordId) {
            this.recordId = recordId;
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
    @Builder
    public static class Response {
        private Long recordId;
//        private LocalDateTime createdAt;
        private Integer firstTeam;
        private Integer secondTeam;
        private Integer firstTeamScore;
        private Integer secondTeamScore;
//        private List<CommentDto.Response> comments;
    }
}
