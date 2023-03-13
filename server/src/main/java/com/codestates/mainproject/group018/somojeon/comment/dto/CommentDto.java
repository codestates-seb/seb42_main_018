package com.codestates.mainproject.group018.somojeon.comment.dto;

import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private Long recordId;

        private String content;

        public void addRecordId(Long recordId) {
            this.recordId = recordId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private Long commentId;
        private Long recordId;

        private String content;

        public void addCommentId(Long commentId) {
            this.commentId = commentId;
        }

        public void addRecordId(Long recordId) {
            this.recordId = recordId;
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long commentId;
        private Long userId;
        private String nickName;
        private Images images;
        private String content;
        private LocalDateTime createdAt;
    }
}
