package com.codestates.mainproject.group018.somojeon.record.dto;

import com.codestates.mainproject.group018.somojeon.comment.dto.CommentDto;
import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class RecordDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String firstTeam;
        private String secondTeam;
        private Integer firstTeamScore;
        private Integer secondTeamScore;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private Long recordId;
        private String firstTeam;
        private String secondTeam;
        private Integer firstTeamScore;
        private Integer secondTeamScore;

        public void addRecordId(Long recordId) {
            this.recordId = recordId;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {

        private Long recordId;
        private LocalDateTime createdAt;
        private String firstTeam;
        private String secondTeam;
        private Integer firstTeamScore;
        private Integer secondTeamScore;
        private List<CommentDto.Response> comments;
        private List<TeamDto.Response> teams;
    }
}
