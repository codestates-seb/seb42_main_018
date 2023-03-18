package com.codestates.mainproject.group018.somojeon.record.dto;

import com.codestates.mainproject.group018.somojeon.comment.dto.CommentDto;
import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class RecordDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private Long recordId;

        public void addRecordId(Long recordId) {
            this.recordId = recordId;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private Long recordId;
        private LocalDateTime createdAt;
        private List<CommentDto.Response> comments;
        private List<TeamDto.Response> teams;
    }
}
