package com.codestates.mainproject.group018.somojeon.record.dto;

import com.codestates.mainproject.group018.somojeon.comment.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RecordDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private LocalDate date;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private Long recordId;

        private LocalDate date;

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
        private String place;
        private LocalDate date;
        private LocalDateTime createdAt;
        private List<UserRecordDto.Response> UserRecords;
        private List<CommentDto.Response> comments;

    }
}
